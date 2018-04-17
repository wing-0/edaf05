import sys
testing = 0


def main(args):
    wordsFile = open(args[0], 'r')
    words = []
    for line in wordsFile:
        words.append(line.split('\n')[0])
    wordsFile.close()
    pairsFile = open(args[1], 'r')
    pair1 = []
    pair2 = []
    for line in pairsFile:
        pairs = line.split('\n')[0].split(' ')
        pair1.append(pairs[0])
        pair2.append(pairs[1])
    pairsFile.close()
    graph = buildGraph(words)
    f = open('out', 'wb')
    if(testing):
        for i in range(0, len(pair1)):
            wordPath = BFS(graph, pair1[i], pair2[i])
            print(wordPath)
            f.write(bytes((str(len(wordPath) - 1) + '\n').encode()))
        f.close()
    else:
        for i in range(0, len(pair1)):
            wordPath = BFS(graph, pair1[i], pair2[i])
            print(len(wordPath) - 1)


def buildGraph(words):
    graph = {}
    letterDict = {}
    for w in words:
        oldLetters = []
        for i in range(0, 5):
            fourLetters = ''.join(sorted(w[0:i] + w[i + 1:len(w)]))
            if(fourLetters not in oldLetters):
                oldLetters.append(fourLetters)
                if(fourLetters in letterDict):
                    letterDict[fourLetters].append(w)
                else:
                    letterDict[fourLetters] = [w]

    for w in words:
        fourLetters = ''.join(sorted(w[1:5]))
        # Self-loops are not avoided here, but later instead
        graph[w] = letterDict[fourLetters]
    return graph


def BFS(graph, startWord, endWord):
    if(startWord == endWord):
        return [startWord]
    visited = {}
    pred = {}
    for key in graph:
        visited[key] = 0
    visited[startWord] = 1
    q = [startWord]
    while q:
        v = q.pop(0)
        for w in graph[v]:
            if(not visited[w]):
                visited[w] = 1
                q.append(w)
                pred[w] = v
                if(w == endWord):
                    return path(pred, startWord, endWord)
    return []


def path(pred, startWord, endWord):
    if(startWord == endWord):
        return [startWord]
    else:
        return path(pred, startWord, pred[endWord]) + [endWord]


if __name__ == "__main__":
    main(sys.argv[1:])
