import Stemmer  # For stemming terms

from CreateRetriever import *
from Corpus import *



if __name__ == "__main__":
    stemmer = Stemmer.Stemmer("english")
    corpus_plain, corpus_verbatim = prepareCorpus()
    tokens = tokenizado(corpus_plain, stemmer, "en")
    retriever = createRetriever(corpus_verbatim, stemmer,save=True)