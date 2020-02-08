package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

//component can be tabacco==0, paper==1,matches==2
func smoker(component int, table *[]bool, readSemaphore, writeSemaphore chan bool, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()
	for {
		readSemaphore <- true
		fmt.Println("Checking table # ", component, "...")
		if !(*table)[component] {
			fmt.Println("Smoking # ", component, "...")
			for i := range *table {
				fmt.Println((*table)[i])
				(*table)[i] = false
			}
			writeSemaphore <- true
		} else {
			<-readSemaphore
			time.Sleep(time.Second)
		}
	}
}

func intermediary(table *[]bool, readSemaphore, writeSemaphore chan bool, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()
	for {
		<-writeSemaphore
		var first, second = getCigaretteStuff()
		fmt.Println("Intermediary put new items:", first, "and", second)
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
	var waitGroup sync.WaitGroup
	var table = make([]bool, smockersNum)

	var readSemaphore = make(chan bool)
	var writeSemaphore = make(chan bool, 1)

	writeSemaphore <- true
	waitGroup.Add(1)
	go intermediary(&table, readSemaphore, writeSemaphore, &waitGroup)

	for i := 0; i < smockersNum; i++ {
		waitGroup.Add(1)
		go smoker(i, &table, readSemaphore, writeSemaphore, &waitGroup)
	}
	waitGroup.Wait()
}
