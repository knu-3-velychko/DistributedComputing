#pragma once

#include "HtmlBuilder.h"
#include <vector>
#include <string>
#include <utility>
#include <iostream>

class Test
{
public:
	Test(std::string path);

	void addTask(int size);
	void run();
private:
	std::vector<int> sizes;
	HtmlBuilder* builder;

	int calculate(int size, int threadsNumber);
	void finish();
};

