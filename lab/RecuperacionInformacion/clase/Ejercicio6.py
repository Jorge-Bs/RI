from Corpus import *
import Stemmer
from rerankers import Reranker
from CreateRetriever import *
from Ejercicio5 import *

def combinar_resultados(resultBm25s, resultsChroma):
    combined_results = {}
    for query_id in resultBm25s.keys():
        combined_results[query_id] = resultBm25s[query_id] + resultsChroma[query_id]
    return combined_results

def re_ranker(queries, combined_results):
    re_ranked_results = {}
    ranker = Reranker(model_name)
    for query_id in combined_results.keys():
        documents = combined_results[query_id]
        ranked_docs = ranker.rank(query=queries[query_id], docs=documents)
        re_ranked_results[query_id] = [
            {"text": doc, "score": score} for doc, score in zip(documents, ranked_docs)
        ]
    return re_ranked_results

if __name__=="__main__":
    stemmer = Stemmer.Stemmer("english")
    stopwords = "en"

    corpus_plain, corpus_verbatim = prepareCorpus()
    corpus_tokenized = tokenizado(corpus_plain, stemmer, stopwords)

    retriever = createRetriever(corpus_verbatim, corpus_tokenized, method="lucene", idf_method="lucene")

    collection,model = conectDb()

    queries = loadQueries()


    #Bm25s search

    resultBm25s = processQueries(queries, stemmer, stopwords, retriever, 1)

    #ChromaDb search

    resultsChroma = submit_queries_and_get_run(queries, collection, model,1,show_progress_bar=True)

    combinacion = combinar_resultados(resultBm25s, resultsChroma)

    result = re_ranker(queries, combinacion)

    print(result)
