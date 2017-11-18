/**
 * @function main Debug Version
 * @brief main function as entry point. 
 * @author Krishna
 */
//#include "opencv2\highgui\highgui.hpp"
//#include "opencv2\imgproc\imgproc.hpp"
//#include "opencv2\imgproc\imgproc.hpp"
//#include "opencv2/features2d/features2d.hpp"
//#include "opencv2\core\core.hpp"
#include <iostream>
#include <fstream>
#include <stdio.h>
//#define NOMINMAX
//#include <Windows.h>
#include <string.h>
#include "StitchingNew.cpp"
#include "StitchingRigid.cpp"
#include "ExposureIndexMask.cpp"

#define DllExport __declspec(dllexport)
//extern "C" DllExport void  Stitch(LPSTR path1, LPSTR path2, int direction, int method);
//extern "C" DllExport void StitchNew(LPSTR path1, LPSTR path2, int direction);



/* Method that stitches two images*/
cv::Mat Stitch(cv::Mat image1, cv::Mat image2,int direction,bool crop);
//cv::Mat Stitch_Flann(cv::Mat image1, cv::Mat image2,int direction,bool crop);

//New stitch implementation with direction only
cv::Mat StitchNew(cv::Mat image1, cv::Mat image2, int direction);
cv::Mat StitchNewEdge(cv::Mat image1, cv::Mat image2, cv::Mat original1, cv::Mat original2, int direction);
cv::Mat StitchNew(cv::Mat image1,cv::Mat boneMask1, cv::Mat image2, cv::Mat boneMask2,int direction);
cv::Mat StitchNew(cv::Mat image1, cv::Mat image2, int edgePixelWidth, int direction);
cv::Mat StitchNew(cv::Mat image1,cv::Mat mask1, cv::Mat image2,cv::Mat mask2,int pixelWidth, int direction);
long getImageArea(cv::Mat image);

int main(void)
{
	short mask=0;
	//For message display
	char szBuffer[100];

    #pragma region "Input Images"

	char* path1="/media/krishna/Backup/WorkingFolder/svn_stitching/images/StitchingImages_2013_10_11/1.png";
	char* path2="/media/krishna/Backup/WorkingFolder/svn_stitching/images/StitchingImages_2013_10_11/2.png";
	//char* EMIPath1="/media/krishna/Backup/WorkingFolder/svn_stitching/images/StitchingImages_2013-10-08/2/22.eim";
	//char* EMIPath2="/media/krishna/Backup/WorkingFolder/svn_stitching/images/StitchingImages_2013-10-08/2/3.eim";
	char* originalPath1="/media/krishna/Backup/WorkingFolder/svn_stitching/images/StitchingImages_2013_10_11/1.png";
	char* originalPath2="/media/krishna/Backup/WorkingFolder/svn_stitching/images/StitchingImages_2013_10_11/2.png";

	cv::Mat image1=cv::imread(path1,CV_LOAD_IMAGE_ANYDEPTH|CV_LOAD_IMAGE_GRAYSCALE);
	cv::Mat image2=cv::imread(path2,CV_LOAD_IMAGE_ANYDEPTH|CV_LOAD_IMAGE_GRAYSCALE);
	cv::Mat original1=cv::imread(originalPath1,CV_LOAD_IMAGE_ANYDEPTH|CV_LOAD_IMAGE_GRAYSCALE);
	cv::Mat original2=cv::imread(originalPath2,CV_LOAD_IMAGE_ANYDEPTH|CV_LOAD_IMAGE_GRAYSCALE);

//	ExposureIndexMask* exposureMask=new ExposureIndexMask();
//	cv::Mat boneMask1=exposureMask->loadExposureIndexMaskFile(EMIPath1);
//	cv::Mat boneMask2=exposureMask->loadExposureIndexMaskFile(EMIPath2);




	if(!image1.data ||!image2.data||!original1.data||!original2.data){
		printf("Error: Image Not Found!");
		std::getchar();
		exit(0);
	}

	//if(image1.depth()!=CV_16U || image2.depth()!=CV_16U){
	//	printf("\nThis application needs 16 bits images to work!");
	//	std::getchar();
	//	exit(0);
	//}

	//cv::Mat image1Mask=image1.mul(boneMask1);
	//cv::Mat image2Mask=image2.mul(boneMask2);

	cv::imwrite("output/image1.png",image1);
	cv::imwrite("output/image2.png",image2);
	cv::imwrite("output/original1.png",original1);
	cv::imwrite("output/original2.png",original2);

	int64 tick=cv::getTickCount();

	//cv::Mat stitchedImage=StitchNewEdge(image1,image2,original1, original2,2);
	//cv::Mat stitchedImage=StitchNew(image1,boneMask1, image2,boneMask2,2);
	cv::Mat stitchedImage= StitchNew(image1,image2, 2);
	//cv::Mat stitchedImage=StitchNew(image1, image2, 280,2);
	//cv::Mat stitchedImage=StitchNew(image1, boneMask1, image2, boneMask2,280,2);

	//cv::Mat stitchedImage=Stitch(image1,image2,2,false);

	cv::imwrite("output/stitchedImage.png",stitchedImage);
	float seconds=(cv::getTickCount()-tick)/cv::getTickFrequency();

	printf("Stiching Took %f seconds",seconds);
	//getchar();	
}



cv::Mat StitchNew(cv::Mat image1, cv::Mat image2, int direction){
	for(int i=0;i<image1.rows;i++){
			for(int j=0;j<image1.cols;j++){
				if(image1.at<ushort>(i,j)==0){
					image1.at<ushort>(i,j)=1;
				}
			}
		}
		for(int i=0;i<image2.rows;i++){
			for(int j=0;j<image2.cols;j++){
				if(image2.at<ushort>(i,j)==0){
					image2.at<ushort>(i,j)=1;
				}
			}
		}
		StitchingRigid* stitchingRigid=new StitchingRigid(image1,image2);
		return stitchingRigid->performOverallStitch(direction,true);
}

cv::Mat StitchNewEdge(cv::Mat image1, cv::Mat image2, cv::Mat original1, cv::Mat original2, int direction){
	for(int i=0;i<image1.rows;i++){
		for(int j=0;j<image1.cols;j++){
			if(image1.at<ushort>(i,j)==0){
				image1.at<ushort>(i,j)=1;
			}
		}
	}
	for(int i=0;i<image2.rows;i++){
		for(int j=0;j<image2.cols;j++){
			if(image2.at<ushort>(i,j)==0){
				image2.at<ushort>(i,j)=1;
			}
		}
	}
	StitchingRigid* stitchingRigid=new StitchingRigid(image1,image2);
	return stitchingRigid->performOverallStitch(direction,original1,original2);
}

cv::Mat StitchNew(cv::Mat image1,cv::Mat mask1, cv::Mat image2,cv::Mat mask2, int direction){
for(int i=0;i<image1.rows;i++){
		for(int j=0;j<image1.cols;j++){
			if(image1.at<ushort>(i,j)==0){
				image1.at<ushort>(i,j)=1;
			}
		}
	}
	for(int i=0;i<image2.rows;i++){
		for(int j=0;j<image2.cols;j++){
			if(image2.at<ushort>(i,j)==0){
				image2.at<ushort>(i,j)=1;
			}
		}
	}
	StitchingRigid* stitchingRigid=new StitchingRigid(image1,mask1,image2,mask2);
	return stitchingRigid->performOverallStitch(direction,false);
}

cv::Mat StitchNew(cv::Mat image1, cv::Mat image2, int edgePixelWidth, int direction){
	for(int i=0;i<image1.rows;i++){
			for(int j=0;j<image1.cols;j++){
				if(image1.at<ushort>(i,j)==0){
					image1.at<ushort>(i,j)=1;
				}
			}
		}
		for(int i=0;i<image2.rows;i++){
			for(int j=0;j<image2.cols;j++){
				if(image2.at<ushort>(i,j)==0){
					image2.at<ushort>(i,j)=1;
				}
			}
		}
		StitchingRigid* stitchingRigid=new StitchingRigid(image1,image2,edgePixelWidth);
		return stitchingRigid->performOverallStitch(direction,false);
}

cv::Mat StitchNew(cv::Mat image1,cv::Mat mask1, cv::Mat image2,cv::Mat mask2,int pixelWidth, int direction){
for(int i=0;i<image1.rows;i++){
		for(int j=0;j<image1.cols;j++){
			if(image1.at<ushort>(i,j)==0){
				image1.at<ushort>(i,j)=1;
			}
		}
	}
	for(int i=0;i<image2.rows;i++){
		for(int j=0;j<image2.cols;j++){
			if(image2.at<ushort>(i,j)==0){
				image2.at<ushort>(i,j)=1;
			}
		}
	}
	StitchingRigid* stitchingRigid=new StitchingRigid(image1,mask1,image2,mask2,pixelWidth);
	return stitchingRigid->performOverallStitch(direction,false);
}

//void Stitch(LPSTR path1, LPSTR path2, int direction,int method,bool crop){
//	cv::Mat image1=cv::imread(path1,CV_LOAD_IMAGE_ANYDEPTH|CV_LOAD_IMAGE_GRAYSCALE);
//	cv::Mat image2=cv::imread(path2,CV_LOAD_IMAGE_ANYDEPTH|CV_LOAD_IMAGE_GRAYSCALE);
//	if(method==0){
//		Stitch(image1,image2,direction,crop);
//	}else{
//		Stitch_Flann(image1,image1,direction,crop);
//	}
//}

//void StitchNew(LPSTR path1, LPSTR path2, int direction){
//	cv::Mat image1=cv::imread(path1,CV_LOAD_IMAGE_ANYDEPTH|CV_LOAD_IMAGE_GRAYSCALE);
//	cv::Mat image2=cv::imread(path2,CV_LOAD_IMAGE_ANYDEPTH|CV_LOAD_IMAGE_GRAYSCALE);
//	StitchNew(image1,image2,direction);
//}


long getImageArea(cv::Mat image){
	return image.rows*image.cols;
}




