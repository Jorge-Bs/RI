import bm25s

def corpusContent():
    # Cargamos el archivo train.docs, y lo separamos por el salto de línea
    with open("../archivos/train.docs", "r", encoding="utf-8") as f:
        corpus_content = f.read()
        corpus_content = corpus_content.split("\n")

    return corpus_content

def prepareCorpus():

    corpus_content = corpusContent()

    # Inicializamos unas lista vacías
    corpus_plainText = list()
    corpus_verbatim = list()

    # Recorremos el corpus_content y lo dividimos en tres partes, el id, el título y el texto
    for valor in corpus_content:
        partes = valor.split("\t")
        valor = partes[1].lower()
        document = {"id": partes[0], "title": partes[0].lower(), "text": valor}
        corpus_verbatim.append(document)
        corpus_plainText.append(valor)

    return corpus_plainText, corpus_verbatim


def tokenizado(texto, stemmer, stopwords):
    # Tokenizamos el corpus
    corpus_tokenized = bm25s.tokenize(texto, stopwords=stopwords, stemmer=stemmer, show_progress=False)

    return corpus_tokenized