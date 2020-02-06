package main

import (
	"fmt"
	"math/rand"
	"time"
)

//component can be tabacco==0, paper==1,matches==2
func smoker(component int, table *[]bool, readSemaphore, writeSemaphore chan bool) {
	for {
		readSemaphore <- true
		fmt.Println("Checking table # ", component, "...")
	}
}

func intermediary(table *[]bool, readSemaphore, writeSemaphore chan bool) {
	for {
		<-writeSemaphore
		fmt.Println("Intermediary put new items:")
		var first, second = getCigaretteStuff()
		(*table)[first] = true
		(*table)[second] = true
		<-readSemaphore
	}
}

func getCigaretteStuff() (int, int) {
	r := rand.New(rand.NewSource(time.Now().UnixNano()))

	stuff1 := r.Intn(3)
	stuff2 := r.Intn(3)
	for stuff2 == stuff1 {
		stuff2 = r.Intn(3)
	}

	return stuff1, stuff2
}

func main() {
	const smockersNum = 3
	var table = make([]bool, smockersNum)

	var readSemaphore = make(chan bool)
	var writeSemaphore = make(chan bool)

	writeSemaphore <- true
	go intermediary(&table, readSemaphore, writeSemaphore)

	for i := 0; i < smockersNum; i++ {
		go smoker(i, &table, readSemaphore, writeSemaphore)
	}
}
