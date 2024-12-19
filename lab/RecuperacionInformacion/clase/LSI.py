"Paquetes"

import numpy as np
from sklearn.decomposition import TruncatedSVD
from sklearn.neighbors import NearestNeighbors

# Nuestra colección de documentos a indexar
#
pizzas = {
    "caprichosa": "alcachofas mozzarella champiñones aceite aceitunas tomate",
    "cuatro quesos": "fontina gorgonzola mozzarella tomate stracchino",
    "marinera": "ajo aceite orégano tomate",
    "romana": "anchoas mozzarella aceite orégano tomate",
    "vienesa": "mozzarella aceite orégano salchicha tomate"
}

print(f"La colección:\n{pizzas}")

# Función para extraer el vocabulario de la colección
#
def obtener_vocabulario(coleccion):
    vocabulario = set()

    for contenido in coleccion.values():
        vocabulario.update(contenido.split()) # En vez de split sería prudente tokenizar de otro modo

    # Convertimos el conjunto a una lista y lo ordenamos alfabéticamente
    vocabulario_ordenado = sorted(vocabulario)

    # Creamos un diccionario que asigna un índice a cada palabra
    vocabulario_dict = {palabra: id_ for id_, palabra in enumerate(vocabulario_ordenado)}

    return vocabulario_dict

# Se extrae el vocabulario
#
vocabulario = obtener_vocabulario(pizzas)
print(f"\nEl vocabulario de la colección:\n{vocabulario}")