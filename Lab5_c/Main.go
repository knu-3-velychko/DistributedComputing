package main

import "sync"

type CyclicBarrier struct {
    generation int
    count      int
    parties    int
    trip       *sync.Cond
}

func (b *CyclicBarrier) nextGeneration() {
    b.trip.Broadcast()
    b.count = b.parties
    b.generation++
}

func (b *CyclicBarrier) Await() {
    b.trip.L.Lock()
    defer b.trip.L.Unlock()

    generation := b.generation

    b.count--
    index := b.count

    if index == 0 {
        b.nextGeneration()
    } else {
        for generation == b.generation {
            //wait for current generation complete
            b.trip.Wait()
        }
    }
}

func NewCyclicBarrier(num int) *CyclicBarrier {
    b := CyclicBarrier{}
    b.count = num
    b.parties = num
    b.trip = sync.NewCond(&sync.Mutex{})

    return &b
}

func arrayModifier(array int[],barrier CyclicBarrier){
	for{
		barrier.Await()
	}
}

func controller() {
	for {
		barrier := NewCyclicBarrier(3)

	}
}

func main() {
}
