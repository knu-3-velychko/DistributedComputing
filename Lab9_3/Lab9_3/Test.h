#pragma once

#include "MatrixGenerator.h"
#include "StripesSchema.h"
#include "HtmlBuilder.h"
#include <vector>
#include <string>
#include <utility>
#include <iostream>
#include <chrono>

class Test
{
public:
	Test(std::string path);

	void addTask(int size);
	void run();
private:
	std::vector<int> sizes;
	HtmlBuilder* builder;

	double calculate(int size, int threadsNumber);
	void finish();
};

