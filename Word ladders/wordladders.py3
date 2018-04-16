import sys


def main(args):
    # args[0] and args[1] are assumed to be without file extension
    wordsFile = open(args[0] + '.txt', 'r')
    words = []
    for line in wordsFile:
        words.append(line.split('\n')[0])
    pairsFile = open(args[1] + '.txt', 'r')
    pair1 = []
    pair2 = []
    for line in pairsFile:
        pairs = line.split(' ')
        pair1 = pairs[0]
        pair2 = pairs[1]
    graph = buildGraph(words)


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
    print(graph)


if __name__ == "__main__":
    main(sys.argv[1:])
