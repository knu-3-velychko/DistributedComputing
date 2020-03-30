// Lab9_3.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "Test.h"
#include <iostream>
#include <tbb/parallel_for.h>

int main()
{
	auto test = new Test("/resoutce/index.html");
	test->addTask(100);
	test->addTask(500);
	test->addTask(1000);
	test->addTask(1500);
	test->addTask(2000);
	test->addTask(2500);
	test->addTask(3000);
	test->run();
}
