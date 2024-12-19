

def fileName(name):
     steamer = ["NON-Stemming", "Stemming"]
     stopWords = ["NON-Stopwords", "Stopwords"]

     for st in steamer:
         for stop in stopWords:
             file = name+"-"+stop+"-"+st+".txt"

             contador = 0
             valor =""

             with open(f"{file}", "r", encoding="utf-8") as f:
                 print(file+":")
                 archivo=f.read()
                 lineas = archivo.split("\n")
                 for i in lineas:
                     parts = i.split(":")
                     if len(parts)>=2:
                        if contador<3:
                            contador+=1
                            if contador ==3:
                                valor = valor +"__" +parts[1]+"__" + "|"
                                print(valor)
                                contador=0
                                valor=""
                            else:
                                valor = valor + parts[1] + "|"




if __name__ == "__main__":
    archivo = input("Ingrese el nombre del archivo: ")
    fileName(archivo)