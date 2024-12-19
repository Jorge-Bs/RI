import bm25s # To create indexes and search documents using BM25


def createRetriever(corpus_verbatim, corpus_tokenized,method="bm25l",idf_method="bm25l",save=False):

    retriever = bm25s.BM25(corpus=corpus_verbatim, method=method, idf_method=idf_method)
    retriever.index(corpus_tokenized, show_progress=False)

    if save:
        retriever.save("../archivos/NFcorpus",corpus=corpus_verbatim)

    return retriever

def openRetriever():
    retriever = bm25s.BM25.load("../archivos/NFcorpus",load_corpus=True)
    return retriever