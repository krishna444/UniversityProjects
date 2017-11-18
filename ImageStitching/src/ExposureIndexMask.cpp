/*
 * ExposureIndexMask.cpp
 *
 *  Created on: Sep 14, 2013
 *      Author: krishna
 */
#include <opencv/cv.h>
#include <iostream>
#include <fstream>

class ExposureIndexMask {

public:
	ExposureIndexMask() {

	}
	cv::Mat loadExposureIndexMaskFile(char* path) {
		int width, height;
		std::ifstream inFile;
		std::ifstream::pos_type size;
		inFile.open(path, std::ios::in | std::ios::binary | std::ios::ate);
		size = inFile.tellg();
		int fileSize = size;
		inFile.seekg(0, std::ios::beg);
		char* oData = new char[size];

		inFile.read(oData, size);

		unsigned char* byte = (unsigned char*) oData;
		width = byte[1] << 8 | byte[0];
		height = byte[3] << 8 | byte[2];

		unsigned short* mask = new unsigned short[width * height];
		int index = 0;

		for (int i = 4; i < fileSize; i++) {
			for (int j = 7; j >= 0; j--) {
				if ((byte[i] & (0x1 << j)) == 0) {
					mask[index++] = 0;
				} else {
					mask[index++] = 1;
				}
			}

		}
		return cv::Mat(height * width, 1, CV_16U, mask).reshape(1, height);
	}
};
