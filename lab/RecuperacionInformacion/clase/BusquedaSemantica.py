import chromadb
import json
from chromadb.utils import embedding_functions
from sentence_transformers import SentenceTransformer

# "Loading the corpus"
# with open("lisa-corpus.json", "r", encoding="utf-8") as f:
#     corpus_content = f.read()
#     corpus_content = json.loads(corpus_content)
#
# # "Creating the collection with ChromaDB"
# # # Initialize the sentence transformer model
# model = SentenceTransformer('sentence-transformers/paraphrase-multilingual-MiniLM-L12-v2')
# #
# # # Create a persistent ChromaDB client
# client = chromadb.PersistentClient(path="./chromadb-storage/")
# #
# # # We create the collection, please note how we are providing the embedding
# # # pre-trained model (this is a multilingual model) and we specify the
# # # distance metric to find the nearest neighbors
# collection = client.create_collection(
#    name="LISA_collection",
#    embedding_function=embedding_functions.SentenceTransformerEmbeddingFunction(model_name="sentence-transformers/paraphrase-multilingual-MiniLM-L12-v2"),
#    metadata={"hnsw:space": "cosine"} # https://docs.trychroma.com/guides#changing-the-distance-function
# )
#
#
#  # Prepare documents for ChromaDB
# chromadb_documents = []
# chromadb_doc_ids = []
#
# for document in corpus_content:
#     doc_id = str(document["id"])
#     title = document["title"].lower()
#     content = document["content"].lower()
#
#     chromadb_doc_ids.append(doc_id)
#     chromadb_documents.append(f"{title} {content}")

"Crea los embeddings"
# #chromadb_embeddings = model.encode(chromadb_documents, batch_size=100, show_progress_bar=True , device='cuda')

import pickle

with open("LISA-embeddings.pickle", "rb") as f:
  chromadb_embeddings = pickle.load(f)
print(chromadb_embeddings[0])

# Function to split a list (of documents, ids, embeddings) into batches because
# of some issues with ChromaDB trying to add to large dataset in a single step
# to a collection
def get_batches(lista, chunk_size=100):
    return [lista[i:i + chunk_size] for i in range(0, len(lista), chunk_size)]