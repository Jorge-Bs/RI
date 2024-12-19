from Ejercicio3 import *
from Ejercicio2 import *

def compute_precision_recall_f1(run, relevance_judgements):
    # Initialize lists to hold precision, recall, and f1 scores for each query
    query_id = "PLAIN-10"


    precision_values = []
    recall_values = []
    f1_values = []

    # Initialize global counts for micro-averaging
    global_retrieved = 0
    global_relevant = 0
    global_retrieved_and_relevant = 0

    # Compute precision, recall, and F1 score for each query
    retrieved_results = [doc["id"] for doc in run.documents[0]]
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



if __name__ == "__main__":
    relevanceJudgements = loadRelevanceJudgements()
    for n in range (1,6):
        for m in range(3,6):
           query,result = ejecucion(n,m,"how contaminated are our children ?")

           precision = compute_precision_recall_f1(result, relevanceJudgements)

           print(f"Valor de n:{n} , Valor de m:{m}")
           print(precision)