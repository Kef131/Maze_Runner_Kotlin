package mazerunner

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
        //Create passages
        correctPassageSimple(entrance, exit)
        createRandomPassages(entrance, 1)

    }

    // Create random passages to avoid 3x3 wall blocks and make all empty cells accessible
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


    private fun createEmptyPassages3x3(entrance: Int, i: Int) {
        for (i in 1..8) {
            for (j in 1..8) {
                //verify if the current block as all the 8 surrounding blocks as walls
                if (maze[i][j] == 1 && maze[i - 1][j] == 1 && maze[i + 1][j] == 1 && maze[i][j - 1] == 1 && maze[i][j + 1] == 1 && maze[i - 1][j - 1] == 1 && maze[i - 1][j + 1] == 1 && maze[i + 1][j - 1] == 1 && maze[i + 1][j + 1] == 1) {
                    maze[i][j] = 0
                }

            }
        }

    }

    //correct passage to exit
    fun correctPassageSimple(entrance: Int, exit: Int) {
        //horizontal passage from entrance
        for (i in 1..8) {
            maze[entrance][i] = 0
        }

        //vertical passage to exit
        if (exit > entrance) { // exit is above entrance
            for (i in entrance..exit) {
                maze[i][8] = 0
            }
        } else { // exit is below entrance
            for (i in exit..entrance) {
                maze[i][8] = 0
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