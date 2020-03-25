package main

import (
	"math/rand"
	"sync"
	"time"
)

func generateMatrix(size int, maxValue float32) [][]float32 {
	matrix := make([][]float32, size)
	r := rand.New(rand.NewSource(time.Now().UnixNano()))

	for i := range matrix {
		matrix[i] = make([]float32, size)
		for j := range matrix[i] {
			matrix[i][j] = maxValue * r.Float32()
		}
	}

	return matrix
}

func stripesMethod(A, B [][]float32, threadsNumber int) [][]float32 {
	C := make([][]float32, len(A))

	for i := range C {
		C[i] = make([]float32, len(A))
	}

	var waitgroup sync.WaitGroup

	var taskSize int = len(A) / threadsNumber

	for i := 0; i < threadsNumber; i++ {
		var rowStart, rowEnd, columnStart, columnEnd int

		waitgroup.Add(threadsNumber)

		for j := 0; j < threadsNumber; j++ {
			rowStart = j * taskSize
			if j == threadsNumber-1 {
				rowEnd = len(A)
			} else {
				rowEnd = (j + 1) * taskSize
			}

			columnStart = ((i + j) % threadsNumber) * taskSize
			if columnStart/taskSize == threadsNumber-1 {
				columnEnd = len(A)
			} else {
				columnEnd = columnStart + taskSize
			}

			compute(A, B, C, rowStart, rowEnd, columnStart, columnEnd, &waitgroup)
		}

		waitgroup.Wait()
	}

	return C
}

func compute(A, B, C [][]float32, rowStart, rowEnd, columnStart, columnEnd int, waitgroup *sync.WaitGroup) {
	for i := rowStart; i < rowEnd; i++ {
		for j := columnStart; j < columnEnd; j++ {
			C[i][j] = calculateEntry(A, B, i, j)
		}
	}
	waitgroup.Done()
}

func calculateEntry(A, B [][]float32, i, j int) float32 {
	var result float32
	result = 0.0
	for k := 0; k < len(B); k++ {
		result += A[i][k] * B[k][j]
	}

	return result
}

func test() {
	//TODO: create test for each matrix size
}

func main() {

}
