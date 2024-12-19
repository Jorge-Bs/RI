import chromadb#Important use chromadb version <0.5.4 due to access violation error -1073741819 (0xC0000005)
from chromadb.utils import embedding_functions
from sentence_transformers import SentenceTransformer
from Ejercicio2 import *
import gzip
import pickle

model_name="intfloat/multilingual-e5-large-instruct"
#model_name="sentence-transformers/paraphrase-multilingual-MiniLM-L12-v2"

def create_db():
    # Initialize the sentence transformer model
    model = SentenceTransformer(model_name)

    # Create a persistent ChromaDB client
    client = chromadb.PersistentClient(path="../archivos/chromadb-storage/")
    collection = client.create_collection(
        name="NFCorpus",
        embedding_function=embedding_functions.SentenceTransformerEmbeddingFunction(
            model_name=model_name),
        metadata={"hnsw:space": "cosine"}
    )
    return model,collection

def process_documents(model):
    corpus_content = corpusContent()
    chromadb_documents = []
    chromadb_doc_ids = []

    for document in corpus_content:
        parts = document.split("\t")
        doc_id = parts[0]
        title = parts[0].lower()
        content = parts[1].lower()

        chromadb_doc_ids.append(doc_id)
        chromadb_documents.append(f"{title} {content}")

    #chromadb_embeddings = model.encode(chromadb_documents, batch_size=1, show_progress_bar=True , device='cuda')

    #save_embeddings(chromadb_embeddings, "../archivos/(e5-large)embeddings.pkl.gz")

    chromadb_embeddings= load_embeddings("../archivos/(e5-large)embeddings.pkl.gz")

    return  chromadb_embeddings,chromadb_doc_ids,chromadb_documents

def get_batches(lista, chunk_size=1):
    return [lista[i:i + chunk_size] for i in range(0, len(lista), chunk_size)]


def add_documents(collection, chromadb_doc_ids, chromadb_documents, chromadb_embeddings):
    document_batches = get_batches(chromadb_documents)
    ids_batches = get_batches(chromadb_doc_ids)
    embedding_batches = get_batches(chromadb_embeddings)

    for i in range(len(document_batches)):
        documents = document_batches[i]
        doc_ids = ids_batches[i]
        embeddings = embedding_batches[i]

        # Add the documents, ids and embeddings to the collection
        collection.add(
            documents=documents,
            ids=doc_ids,
            embeddings=embeddings
        )

def save_embeddings(embeddings, filename):
    with gzip.open(filename, 'wb') as f:
        pickle.dump(embeddings, f)

def load_embeddings(filename):
    with gzip.open(filename, 'rb') as f:
        embeddings = pickle.load(f)
    print(embeddings[0])
    return embeddings

def conectDb(path="../archivos/chromadb-storage/"):
    model = SentenceTransformer(model_name)

    client = chromadb.PersistentClient(path=path)
    collection = client.get_collection("NFCorpus",
                                       embedding_function=embedding_functions.SentenceTransformerEmbeddingFunction(
                                           model_name=model_name))

    return collection,model

def submit_queries_and_get_run(queries, collection, model,max_results=10,show_progress_bar=True):
    # Initialize the run dictionary
    run = {}

    # Process each query
    # Convert all query texts to lowercase
    query_texts = [queries[query_id].lower() for query_id in queries.keys()]

    # Encode all queries at once
    #query_embeddings = model.encode(query_texts, show_progress_bar=show_progress_bar, device='cuda')

    # Submit all queries to the collection and get the results
    results = collection.query(
        #query_embeddings=query_embeddings,
        query_texts=query_texts,
        n_results=max_results
    )

    # Store the result IDs in the run dictionary
    for i, query_id in enumerate(queries.keys()):
        run[query_id] = results['ids'][i]

    return run

if __name__ == "__main__":


    #model,collection = create_db()
    #chromadb_embeddings,chromadb_doc_ids,chromadb_documents =process_documents(model)
    #add_documents(collection, chromadb_doc_ids, chromadb_documents, chromadb_embeddings)
    collection,model = conectDb()
    query = {"PLAIN-10":"how contaminated are our children ?"}#Same query as in the exercise 4 and 3
    run = submit_queries_and_get_run(query, collection,model)
    print(run)
    relevance = loadRelevanceJudgements()
    result = compute_precision_recall_f1(run, relevance)
    print(result)
