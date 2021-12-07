package advent_of_code_2021.day5.part1

import org.assertj.core.api.Assertions.assertThat

fun main() {
    colinearHorizontalIntersections()
    colinearVerticalIntersections()
    crossIntersections()
    resolve()
}

private fun resolve() {
    val result = resolve("day5/part1/test_input.txt")
    assertThat(result).isEqualTo(5)
}

//    0123456789
//    ..11112211
//    ...1121111
//    1111111...
private fun colinearHorizontalIntersections() {
    assertThat(intersectPoints(Line.from("2,1 -> 7,1"), Line.from("6,1 -> 9,1"))).contains(Point(6, 1), Point(7, 1))
    assertThat(intersectPoints(Line.from("6,1 -> 9,1"), Line.from("2,1 -> 7,1"))).contains(Point(6, 1), Point(7, 1))

    assertThat(intersectPoints(Line.from("3,1 -> 5,1"), Line.from("5,1 -> 9,1"))).contains(Point(5, 1))
    assertThat(intersectPoints(Line.from("5,1 -> 9,1"), Line.from("3,1 -> 5,1"))).contains(Point(5, 1))

    assertThat(intersectPoints(Line.from("0,1 -> 2,1"), Line.from("3,1 -> 6,1"))).isEmpty()
    assertThat(intersectPoints(Line.from("3,1 -> 6,1"), Line.from("0,1 -> 2,1"))).isEmpty()
}

//    0123456789
//    ..11112211
//    ...1121111
//    1111111...
private fun colinearVerticalIntersections() {
    assertThat(intersectPoints(Line.from("1,2 -> 1,7"), Line.from("1,6 -> 1,9"))).contains(Point(1, 6), Point(1, 7))
    assertThat(intersectPoints(Line.from("1,6 -> 1,9"), Line.from("1,2 -> 1,7"))).contains(Point(1, 6), Point(1, 7))

    assertThat(intersectPoints(Line.from("1,3 -> 1,5"), Line.from("1,5 -> 1,9"))).contains(Point(1, 5))
    assertThat(intersectPoints(Line.from("1,5 -> 1,9"), Line.from("1,3 -> 1,5"))).contains(Point(1, 5))

    assertThat(intersectPoints(Line.from("1,0 -> 1,2"), Line.from("1,3 -> 1,6"))).isEmpty()
    assertThat(intersectPoints(Line.from("1,3 -> 1,6"), Line.from("1,0 -> 1,2"))).isEmpty()
}

//    0123456789
//    .......211
//    .......1..
//    .......1..
//    .......1..
//    ....1111..
//    .......1..
//    .......111
private fun crossIntersections() {
    assertThat(intersectPoints(Line.from("7,0 -> 7,5"), Line.from("6,4 -> 9,4"))).contains(Point(7, 4))
    assertThat(intersectPoints(Line.from("6,4 -> 9,4"), Line.from("7,0 -> 7,5"))).contains(Point(7, 4))

    assertThat(intersectPoints(Line.from("7,0 -> 7,5"), Line.from("4,4 -> 6,4"))).isEmpty()
    assertThat(intersectPoints(Line.from("4,4 -> 6,4"), Line.from("7,0 -> 7,5"))).isEmpty()
    assertThat(intersectPoints(Line.from("7,0 -> 7,5"), Line.from("7,6 -> 9,6"))).isEmpty()
    assertThat(intersectPoints(Line.from("7,6 -> 9,6"), Line.from("7,0 -> 7,5"))).isEmpty()

    assertThat(intersectPoints(Line.from("7,0 -> 7,5"), Line.from("7,0 -> 9,0"))).contains(Point(7, 0))
    assertThat(intersectPoints(Line.from("7,0 -> 9,0"), Line.from("7,0 -> 7,5"))).contains(Point(7, 0))
}
