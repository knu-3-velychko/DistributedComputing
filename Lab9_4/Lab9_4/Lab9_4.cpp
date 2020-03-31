// Lab9_4.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "Test.h"
#include <iostream>

int main()
{
	auto test = new Test("index.html");

	test->addTask(100);
	test->addTask(500);
	test->addTask(1000);
	test->addTask(1500);
	test->addTask(2000);
	test->addTask(2500);
	test->addTask(3000);

	test->run();
}
