import math

import Stemmer  # For stemming terms
from collections import Counter
from CreateRetriever import *
from Corpus import *
from Ejercicio2 import *
import threading


def processQueries(query, stemmer, stopwords, retriever, n):

    tokens = tokenizado(query, stemmer, stopwords)
    results = retriever.retrieve(tokens, corpus=retriever.corpus, return_as="tuple", show_progress=True, k=n)

    return  results


def frequency(corpus_tokenized):

    tmp = dict()

    for document in corpus_tokenized[0]:
        freqs = dict(Counter(document))
        for token, freq in freqs.items():
            try:
                tmp[token] += freq
            except:
                tmp[token] = freq

    inverted_vocab = {corpus_tokenized[1][key]: key for key in corpus_tokenized[1].keys()}

    total_freqs = dict()

    for key, freq in dict(tmp).items():
        term = inverted_vocab[key]
        total_freqs[term] = freq


    return total_freqs


def x_log_x(x):
    return 0.0 if x == 0 else x * math.log(x)

def entropy(*elements):
    total = sum(elements)
    result = sum(x_log_x(element) for element in elements)
    return x_log_x(total) - result

def log_likelihood_ratio(k11, k12, k21, k22):
    row_entropy = entropy(k11 + k12, k21 + k22)
    column_entropy = entropy(k11 + k21, k12 + k22)
    matrix_entropy = entropy(k11, k12, k21, k22)
    if row_entropy + column_entropy < matrix_entropy:
        return 0.0
    return 2.0 * (row_entropy + column_entropy - matrix_entropy)

def root_log_likelihood_ratio(k11, k12, k21, k22):
    llr = log_likelihood_ratio(k11, k12, k21, k22)
    sqrt_llr = math.sqrt(llr)
    if (k11 / (k11 + k12)) < (k21 / (k21 + k22)):
        sqrt_llr = -sqrt_llr
    return sqrt_llr

def getBestWords(frequency, othersDocumentsFrequency, queryTokenize, m):
    relevantsWords = sum(frequency.values())
    othersWords = sum(othersDocumentsFrequency.values())

    relevances = dict()

    for word in frequency:
        k11 = frequency[word]
        k12 = relevantsWords - k11
        k21 = othersDocumentsFrequency[word]
        k22 = othersWords - k21

        llr = root_log_likelihood_ratio(k11, k12, k21, k22)
        relevances[word] = llr

    relevances = dict(sorted(relevances.items(), key=lambda item: item[1], reverse=True))

    result = []
    contador = 0

    for key in relevances.keys():
        if key not in queryTokenize:
            result.append(key)
            contador += 1
        if contador >= m:
            break

    return result


def updateQuery(query, bestWords):
    for word in bestWords:
        query += " "+word

    return query

def resta(dic1, dic2):
    result = dict()
    for key in dic1.keys():
        result[key] = dic1[key] - dic2.get(key, 0)
    return result

def ejecucion(n,m,query):

    stemmer = Stemmer.Stemmer("english")
    stopwords = "en"

    corpus_plain,corpus_verbatim = prepareCorpus()
    corpus_tokenized = tokenizado(corpus_plain, stemmer, stopwords)

    retriever = createRetriever(corpus_verbatim, corpus_tokenized, method="lucene", idf_method="lucene")
    #retriever = openRetriever()

    #query = "how contaminated are our children ?"  # primera query
    #queries = loadQueries()



    return procesar(corpus_tokenized, m, n, query, retriever, stemmer, stopwords)
    #return updateQuerry, result


def procesar(corpus_tokenized, m, n, query, retriever, stemmer, stopwords):
    query = query

    documents = processQueries(query, stemmer, stopwords, retriever, n)
    documentsTokenized = tokenizado([doc["text"] for doc in documents.documents[0]], stemmer, stopwords)

    fre = frequency(documentsTokenized)

    othersDocumentsFrequency = frequency(corpus_tokenized)

    othersDocumentsFrequency = resta(othersDocumentsFrequency, fre)

    bestWords = getBestWords(fre, othersDocumentsFrequency, tokenizado(query, stemmer, stopwords)[1], m)

    updateQuerry = updateQuery(query, bestWords)

    result = processQueries(updateQuerry, stemmer, stopwords, retriever, 100)

    return updateQuerry, result


if __name__ == "__main__":
    queries = loadQueries()

    for key in queries.keys():
        ejecucion(5, 3, queries[key])



