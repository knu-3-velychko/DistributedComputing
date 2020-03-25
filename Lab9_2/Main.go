package main

import (
	"fmt"
	"math/rand"
	"os"
	"strconv"
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

func createHTML(name string) *os.File {
	f, err := os.Create(name + ".html")
	if err != nil {
		fmt.Println(err)
		return nil
	}

	return f
}

func addHead(f *os.File) {
	_, err := f.WriteString(`<html xmlns="http://www.w3.org/1999/xhtml">
	<head><title>Reporting Page</title>
		<link rel="stylesheet" href="style.css"></link>
	</head>`)

	if err != nil {
		fmt.Println(err)
		f.Close()
		return
	}
}

func startBody(f *os.File) {
	_, err := f.WriteString("<body>")

	if err != nil {
		fmt.Println(err)
		f.Close()
		return
	}
}

func finishBody(f *os.File) {
	_, err := f.WriteString("</body>")

	if err != nil {
		fmt.Println(err)
		f.Close()
		return
	}
}

func createTable(f *os.File) {
	_, err := f.WriteString(`<table>
    <tr>
        <th rowspan=\"3\">Matrix size</th>
        <th rowspan=\"3\">Sequential algorithm</th>
        <th colspan=\"4\">Parallel algorithm</th>
    </tr>
    <tr>
        <th colspan=\"2\">2 processes</th>
        <th colspan=\"2\">4 processes</th>
    </tr>
    <tr>
        <th>Time</th>
        <th>Acceleration</th>
        <th>Time</th>
        <th>Acceleration</th>
	</tr>`)

	if err != nil {
		fmt.Println(err)
		f.Close()
		return
	}
}

//FloatPair : type that represents pair class for float32
type FloatPair struct {
	first, second float32
}

func addResult(f *os.File, matrixSize int, sequenialTime float32, results []FloatPair) {
	resultString := "<tr>"
	resultString += "<td>" + strconv.Itoa(matrixSize) + "/<td>"
	resultString += "<td>" + fmt.Sprintf("%f", sequenialTime) + "/<td>"

	for i := range results {
		resultString += "<td>" + fmt.Sprintf("%f", results[i].first) + "</td>"
		resultString += "<td>" + fmt.Sprintf("%f", results[i].second) + "</td>"
	}
	resultString += "</tr>"

	_, err := f.WriteString(resultString)

	if err != nil {
		fmt.Println(err)
		f.Close()
		return
	}
}

func closeHTML(f *os.File) {
	_, err := f.WriteString("</html>")

	if err != nil {
		fmt.Println(err)
		f.Close()
		return
	}

	err = f.Close()
	if err != nil {
		fmt.Println(err)
		return
	}
}

func test() {
	//TODO: create test for each matrix size
}

func main() {

}
