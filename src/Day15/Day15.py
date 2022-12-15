from intervaltree import Interval, IntervalTree

class BeaconExclusionFinder:
    def __init__(self):
        self.rows_to_trees = {}
        self.rows_to_points = {}

    def processFile(self, rows: [str]):
        for row in rows:
            print("starting row")
            equalIndex = 0
            relevantValues = []
            while True:
                equalIndex = row.index("=", equalIndex + 1)
                endOfIntIndex = equalIndex + 1
                while endOfIntIndex != len(row) and row[endOfIntIndex] not in (",", ":"):
                    endOfIntIndex += 1

                relevantValues.append(int(row[equalIndex + 1:endOfIntIndex]))
                if len(relevantValues) == 4:
                    break

            sensorX, sensorY, beaconX, beaconY = relevantValues

            dist = abs(beaconX - sensorX) + abs(beaconY - sensorY)
            for yDist in range(0, dist + 1):
                topY, botY = sensorY - yDist, sensorY + yDist
                # if topY == 2000000 or botY == 2000000:

                xDist = dist - yDist
                intervalStart = max(0, sensorX - xDist)
                intervalEnd = min(4000001, sensorX + xDist + 1)
                myInterval = Interval(intervalStart, intervalEnd)
                if topY >= 0:
                    if myInterval.begin == myInterval.end:
                        if topY not in self.rows_to_points:
                            self.rows_to_points[topY] = set()

                        self.rows_to_points[topY].add(sensorX)
                    else:
                        if topY not in self.rows_to_trees:
                            self.rows_to_trees[topY] = IntervalTree()

                        self.rows_to_trees[topY].add(myInterval)
                if botY <= 4000000:
                    if myInterval.begin == myInterval.end:
                        if botY not in self.rows_to_points:
                            self.rows_to_points[botY] = set()

                        self.rows_to_points[botY].add(sensorX)
                    else:
                        if botY not in self.rows_to_trees:
                            self.rows_to_trees[botY] = IntervalTree()

                        self.rows_to_trees[botY].add(myInterval)

    def calculateBeaconPoints(self, row: int):
        if row in self.rows_to_trees:
            points = 0
            tree = self.rows_to_trees[row]
            tree.merge_overlaps(strict=False)
            for range in tree:
                points += range.end - range.begin + 1

            if row in self.rows_to_points:
                for point in self.rows_to_points[row]:
                    if len(tree[point]) == 0:
                        points += 1
            return points
        elif row in self.rows_to_points:
            return len(self.rows_to_points[row])
        else:
            return 0

    def calculatePointsInRow(self, row: int):
        tree = self.rows_to_trees[row]
        tree.merge_overlaps(strict=False)
        startingLen = sum([point.end - point.begin - (1 if point.end == 4000001 else 0) for point in tree])

        if row in self.rows_to_points:
            for point in self.rows_to_points[row]:
                if point and not tree.overlaps(point):
                    startingLen += 1
                    print("POINT: " + str(point))
        return startingLen

    def calculateDistressPoint(self):
        for row in range(0, 4000001):
            pointsPresent = self.calculatePointsInRow(row)
            if pointsPresent != 4000000:
                print("-------------")
                print("ROW: " + str(row))
                print("POINTS FOUND: " + str(pointsPresent))
                print("TREE: " + str(self.rows_to_trees[row]))
                if row in self.rows_to_points:
                    print("POINTS: " + str(self.rows_to_points[row]))



def part1():
    # with open("Day15_test.txt") as testFile:
    #     beaconLocator = BeaconExclusionFinder()
    #     beaconLocator.processFile(testFile)
    #     # print(beaconLocator.calculateBeaconPoints(10))
    #     beaconLocator.calculateDistressPoint()
    with open("Day15.txt") as realFile:
        beaconLocator = BeaconExclusionFinder()
        beaconLocator.processFile(realFile)
        print("calculating")
        beaconLocator.calculateDistressPoint()
#     print(beaconLocator.calculateBeaconPoints(2000000))

part1()