import bm25s  # To create indexes and search documents using BM25
import Stemmer  # For stemming terms

from Corpus import *

def createRetriever(corpus_verbatim, corpus_tokenized, method="lucene", idf_method="lucene"):
    retriever = bm25s.BM25(corpus=corpus_verbatim, method=method, idf_method=idf_method)
    retriever.index(corpus_tokenized, show_progress=True)

    return retriever


def loadQueries():
    querrys = dict()

    with open("../archivos/train.nontopic-titles.queries", "r", encoding="utf-8") as f:
        content = f.read()

        for line in content.split("\n"):
            parts = line.split("\t")
            try:
                querrys[parts[0]] = parts[1]
            except IndexError:
                pass
    return querrys


def processQueries(queries, stemmer, stopwords, retriever,k=100):
    resultado = {}


    for key in queries.keys():
        query = queries[key]
        tokens = tokenizado([query], stemmer, stopwords)
        results = retriever.retrieve(tokens, corpus=retriever.corpus, return_as="tuple", show_progress=True, k=k)

        documents = results.documents[0]
        scores = results.scores[0]

        ids = []
        for doc in documents:
            ids.append(doc["id"])

        resultado[key] = ids

    return resultado


def loadRelevanceJudgements():
    relevanceJudgements = dict()

    with open("../archivos/train.3-2-1.qrel", "r", encoding="utf-8") as f:
        content = f.read()

        for line in content.split("\n"):
            parts = line.split("\t")
            try:
                if (parts[0] in relevanceJudgements.keys()):
                    data = relevanceJudgements[parts[0]]
                    data[parts[2]] = parts[3]
                else:
                    data = {parts[2]: parts[3]}
                relevanceJudgements[parts[0]] = data
            except IndexError:
                pass

    return relevanceJudgements


def compute_precision_recall_f1(run, relevance_judgements):
    # Initialize lists to hold precision, recall, and f1 scores for each query
    precision_values = []
    recall_values = []
    f1_values = []

    # Initialize global counts for micro-averaging
    global_retrieved = 0
    global_relevant = 0
    global_retrieved_and_relevant = 0

    # Compute precision, recall, and F1 score for each query
    for query_id in run.keys():
        retrieved_results = run[query_id]
        relevant_results = relevance_judgements[query_id]
        relevant_and_retrieved = set(retrieved_results) & set(relevant_results)

        # Update global counts
        global_retrieved += len(retrieved_results)
        global_relevant += len(relevant_results)
        global_retrieved_and_relevant += len(relevant_and_retrieved)

        # Compute precision and recall
        precision = len(relevant_and_retrieved) / len(retrieved_results) if len(retrieved_results) > 0 else 0
        recall = len(relevant_and_retrieved) / len(relevant_results) if len(relevant_results) > 0 else 0

        # Compute F1 score if both precision and recall are non-zero
        if (precision + recall) > 0:
            f1 = 2 * (precision * recall) / (precision + recall)
            f1_values.append(f1)

        # Append precision and recall for the current query
        precision_values.append(precision)
        recall_values.append(recall)

    # Compute macro-averages
    macro_average_precision = sum(precision_values) / len(precision_values) if precision_values else 0
    macro_average_recall = sum(recall_values) / len(recall_values) if recall_values else 0
    macro_average_f1 = sum(f1_values) / len(f1_values) if f1_values else 0

    # Compute micro-averages
    micro_average_precision = global_retrieved_and_relevant / global_retrieved if global_retrieved > 0 else 0
    micro_average_recall = global_retrieved_and_relevant / global_relevant if global_relevant > 0 else 0
    micro_average_f1 = (2 * (micro_average_precision * micro_average_recall) /
                        (micro_average_precision + micro_average_recall)) if (
                                                                                     micro_average_precision + micro_average_recall) > 0 else 0

    result = {"macro_average_precision": round(macro_average_precision, 3)
        , "macro_average_recall": round(macro_average_recall, 3)
        , "macro_average_f1": round(macro_average_f1, 3)
        , "micro_average_precision": round(micro_average_precision, 3)
        , "micro_average_recall": round(micro_average_recall, 3)
        , "micro_average_f1": round(micro_average_f1, 3)}

    return result

def saveResults(precision, method, stop, stem):

    if(stop == None):
        stop = "NON-Stopwords"
    else:
        stop = "Stopwords"

    if(stem == None):
        stem = "NON-Stemming"
    else:
        stem = "Stemming"

    with open(f"../archivos/resultsEj2/{method}-{stop}-{stem}.txt", "x", encoding="utf-8") as f:
        f.write(f"Macro-averaged Precision: {precision['macro_average_precision']}\n")
        f.write(f"Macro-averaged Recall: {precision['macro_average_recall']}\n")
        f.write(f"Macro-averaged F1: {precision['macro_average_f1']}\n")
        f.write("\n")
        f.write(f"Micro-averaged Precision: {precision['micro_average_precision']}\n")
        f.write(f"Micro-averaged Recall: {precision['micro_average_recall']}\n")
        f.write(f"Micro-averaged F1: {precision['micro_average_f1']}\n")


if __name__ == "__main__":
    stemmer = ["english", None]
    stopwords = ["en", None]

    method = ["robertson", "atire", "bm25l", "bm25+", "lucene"]

    corpus = prepareCorpus()

    queries = loadQueries()


    relevanceJudgements = loadRelevanceJudgements()

    for me in method:
        for st in stemmer:
            for stop in stopwords:

                stem = None

                if (st != None):
                    stem = Stemmer.Stemmer(st)

                tokens = tokenizado(corpus[0], stem, stop)
                retriever = createRetriever(corpus[1], tokens, me, me)

                result = processQueries(queries, stem, stop, retriever)

                precision = compute_precision_recall_f1(result, relevanceJudgements)

                saveResults(precision,me,stop,st)
