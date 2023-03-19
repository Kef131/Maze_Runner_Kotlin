package mazerunner

import java.lang.Math.abs

class Maze {
    //Creating empty two-dimensional array
    val maze = Array(10) { Array(10) { 1 } }
    //Each pair represents a direction (up, down, left, or right)
    val directions = listOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))

    init {
        createMaze()
    }

    //Creating maze structure
    fun createMaze() {
        //Create random entrance and exit, leaving first and last rows as walls
        val entrance = (1..8).random()
        val exit = (1..8).random()
        maze[entrance][0] = 0
        maze[exit][9] = 0
        // Set the first wall block in the entrance as a passage
        maze[entrance][1] = 0
        //Create passages
        createComplexCorrectPassage(entrance, 1, exit, 8)
        createRandomPassages(entrance, 1)

    }

    // Create a more complex passage from entrance to exit, ocassionally creates very simples passages to the exit
    fun createComplexCorrectPassage(row: Int, col: Int, targetRow: Int, targetCol: Int) {
        // Base case: Stop when the function reaches the exit position
        if (row == targetRow && col == targetCol) {
            return
        }

        // Calculate the row and column differences between the current position and the exit position
        val rowDiff = targetRow - row
        val colDiff = targetCol - col

        /*
        The row and column differences are calculated to determine the direction in which the current position
        should move to get closer to the exit. The difference helps decide whether the function should
        move up/down (change in row) or left/right (change in column) to create a passage towards the exit.
        The direction is determined by checking if the row/column difference is positive or negative.
         If the difference is positive, the function should move up or right.
         If the difference is negative, the function should move down or left.
         If the difference is 0, the function should not move in that direction.
        */
        //Determine which directions are available to move closer to the exit
        val availableDirections = mutableListOf<Pair<Int, Int>>()
        if (rowDiff != 0) {
            availableDirections.add(Pair(rowDiff / abs(rowDiff), 0))
        }
        if (colDiff != 0) {
            availableDirections.add(Pair(0, colDiff / abs(colDiff)))
        }

        // Shuffle the available directions to randomize the order in which the function explores the maze
        val shuffledDirections = availableDirections.shuffled()

        // Iterate through each direction in the shuffled directions list
        for (dir in shuffledDirections) {
            // Calculate the new position in the maze by adding the row and column changes to the current position
            val newRow = row + dir.first
            val newCol = col + dir.second

            // Check if the new position is within the maze boundaries and if the new position is a wall
            if (newRow in 1..8 && newCol in 1..8 && maze[newRow][newCol] == 1) {
                // Set the new position cell as a passage
                maze[newRow][newCol] = 0
                // Call the createComplexPassage function recursively with the new position as the current position
                createComplexCorrectPassage(newRow, newCol, targetRow, targetCol)
                break
            }
        }
    }


    // Create random passages to avoid 3x3 wall blocks and make all empty cells accessible, is it possibe to create
    // 3x3 wall blocks with this function. It is also possible to create 2x2 passage blocks
    fun createRandomPassages(row: Int, col: Int) {
        // Shuffle the directions list to randomize the order in which the function explores the maze
        val shuffledDirections = directions.shuffled()

        // Iterate through each direction in the shuffled directions list
        for (dir in shuffledDirections) {
            // Calculate the new position in the maze by adding twice the row and column changes to the current position
            // this maintain a wall between the passages and prevent passages from being adjacent, which could create 2x2 wall blocks.
            val newRow = row + dir.first * 2
            val newCol = col + dir.second * 2

            // Check if the new position is within the maze boundaries and if the new position is a wall
            if (newRow in 1..8 && newCol in 1..8 && maze[newRow][newCol] == 1) {
                // Set the cell between the current position and the new position as a passage
                maze[row + dir.first][col + dir.second] = 0
                // Set the new position cell as a passage
                maze[newRow][newCol] = 0
                // Call the createRandomPassages function recursively with the new position as the current position
                createRandomPassages(newRow, newCol)
            }
        }
    }

    fun printMaze() {
        //Printing maze
        for (i in 0..9) {
            for (j in 0..9) {
                print(if (maze[i][j] == 1) "\u2588\u2588" else "  ")
            }
            println()
        }
    }
}