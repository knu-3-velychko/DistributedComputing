package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var sum int = 0

func firstEnsign(firstToSecond chan<- int, stopSygnal chan bool, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()
	for {
		r := rand.New(rand.NewSource(time.Now().UnixNano()))
		value := r.Intn(100)
		select {
		case value := <-stopSygnal:
			if value {
				stopSygnal <- true
				return
			}
		case firstToSecond <- value:
			fmt.Println("Send to second ensign", value, "products")
			time.Sleep(time.Millisecond * 1000)
		default:
			time.Sleep(time.Millisecond * 500)
		}
	}
}

func secondEnsign(firstToSecond <-chan int, secondToThird chan<- int, stopSygnal chan bool, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()
	for {
		select {
		case value := <-firstToSecond:
			fmt.Println("Get", value, "products from first ensign.")
			select {
			case value := <-stopSygnal:
				if value {
					stopSygnal <- true
					return
				}
			case secondToThird <- value:
				fmt.Println("Send", value, "products from second ensign.")
				time.Sleep(time.Millisecond * 1000)
			default:
				time.Sleep(time.Millisecond * 500)
			}
		default:
			time.Sleep(time.Millisecond * 500)
		}
	}
}

func thirdEnsign(secondToThird <-chan int, stopSygnal chan bool, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()
	for {
		select {
		case value := <-stopSygnal:
			if value {
				stopSygnal <- true
				return
			}
		case value := <-secondToThird:
			sum += value
			fmt.Println("Calculated", sum, "products from second ensign.")
		default:
			time.Sleep(time.Millisecond * 500)
		}
	}
}

func main() {
	var waitGroup sync.WaitGroup

	firstToSecond, secondToThird, stopSygnal := make(chan int, 10), make(chan int, 10), make(chan bool, 10)
	waitGroup.Add(3)
	go firstEnsign(firstToSecond, stopSygnal, &waitGroup)
	go secondEnsign(firstToSecond, secondToThird, stopSygnal, &waitGroup)
	go thirdEnsign(secondToThird, stopSygnal, &waitGroup)
	time.Sleep(time.Millisecond * 10000)
	stopSygnal <- true
	waitGroup.Wait()
	close(firstToSecond)
	close(secondToThird)
	fmt.Println("Total count:", sum)
}
