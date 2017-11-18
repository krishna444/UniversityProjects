#include<stdio.h>
#include<time.h>
#include<iostream>
#include "opencv/cv.h"
#include "opencv2/calib3d/calib3d.hpp"
#include "opencv2/contrib/contrib.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/nonfree/features2d.hpp"
#include "opencv2/legacy/legacy.hpp"


#define limitPixels 2250000
//This is overall stitching test
//@author: Krishna Paudel(krishna444@gmail.com)
//2012-10-10
class StitchingNew {
	cv::Mat image1;
	cv::Mat image2;
	//cv::Mat image1_resized;
	//cv::Mat image2_resized;
	int distanceThreshold;
	//int level;
	//Defines the boundry point
	//@Value is the co-ordinate value
	//@Index is image index
	struct Boundry {
		int Value;
		int Index;
	};

	enum ImageInfo {
		NONE, FLOAT, BASE
	};

	enum BlendDirection {
		NODIR, DIR1, DIR2, BOTH
	};
	struct Neighbor {
		ImageInfo Left;
		ImageInfo Top;
		ImageInfo Bottom;
		ImageInfo Right;
	};

	struct BlendMask {
		cv::Mat_<float> Left;
		cv::Mat_<float> Top;
		cv::Mat_<float> Right;
		cv::Mat_<float> Bottom;
	};

public:
	StitchingNew() {
		char* path1 = "images/hc.png";
		char* path2 = "images/vc.png";

		image1 = cv::imread(path1,
				CV_LOAD_IMAGE_ANYDEPTH | CV_LOAD_IMAGE_GRAYSCALE);
		image2 = cv::imread("images/hc.png",CV_LOAD_IMAGE_ANYDEPTH | CV_LOAD_IMAGE_GRAYSCALE);
		distanceThreshold = 1;
		//level = 4;
		performOverallStitch(2);
		/*Neighbor neighbor;
		 neighbor.Top=ImageInfo::FLOAT;
		 neighbor.Left=ImageInfo::BASE;
		 neighbor.Right=ImageInfo::BASE;
		 neighbor.Bottom=ImageInfo::FLOAT;

		 createBlendMask(150,150,neighbor);*/

		getchar();
	}

	StitchingNew(cv::Mat image1, cv::Mat image2) {
		this->image1 = image1;
		this->image2 = image2;
		distanceThreshold = 10;
	}
	~StitchingNew() {
	}

	cv::Mat performOverallStitch() {
		cv::Mat image1_8bit, image2_8bit;
		image1.convertTo(image1_8bit, CV_8U, 1. / 256);
		image2.convertTo(image2_8bit, CV_8U, 1. / 256);
		cv::vector<cv::Mat> images;
		images.push_back(image1_8bit);
		images.push_back(image2_8bit);
		cv::Mat stitchedImage;
		//cv::Stitcher stitcher = cv::Stitcher::createDefault();
		//cv::Stitcher::Status status = stitcher.stitch(images, stitchedImage);
		//std::cout << status;
		cv::imshow("result", stitchedImage);
		return stitchedImage;
	}

	cv::Mat performOverallStitch(int direction) {

		cv::Mat homography = calculateHomography();

		if(homography.size().height==1 && homography.size().width==1){
			printf("ERROR:Stitchign Not Possible");
			return cv::Mat::zeros(image1.size(),CV_16UC1);
		}

       // cv::Mat output;
		//stich(this->image1,this->image2,homography,output);
		//cv::imwrite("output/result.png", output);
		//return output;

		printf("Homography=");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				printf("%f\t", homography.at<double>(i, j));
			}
			printf("\n");
		}

		/*homography.at<double>(2,2) = 1;
		 homography.at<double>(2,0) = homography.at<double>(2,1) = 0;

		 double theta=atan2(homography.at<double>(0,1),homography.at<double>(0,0));

		 homography.at<double>(0,0)=cos(theta);
		 homography.at<double>(0,1)=sin(theta);
		 homography.at<double>(1,0)=-sin(theta);
		 homography.at<double>(1,1)=cos(theta);*/

		cv::Mat warpedImage;
		cv::Point topLeft, bottomRight;
		rotateImage(image1, homography, warpedImage, topLeft, bottomRight);

		cv::imwrite("output/warpedImage.png", warpedImage);
		Boundry left, top, right, bottom;
		if (topLeft.x < 0) {
			left.Index = 0;
			left.Value = topLeft.x;
		} else {
			left.Index = 1;
			left.Value = 0;
		}

		if (topLeft.y < 0) {
			top.Index = 0;
			top.Value = topLeft.y;
		} else {
			top.Index = 1;
			top.Value = 0;
		}

		if (bottomRight.x > image2.cols) {
			right.Index = 0;
			right.Value = bottomRight.x;
		} else {
			right.Index = 1;
			right.Value = image2.cols;
		}

		if (bottomRight.y > image2.rows) {
			bottom.Index = 0;
			bottom.Value = bottomRight.y;
		} else {
			bottom.Index = 1;
			bottom.Value = image2.rows;
		}

		cv::Mat stitchedImage(bottom.Value - top.Value + 1,
				right.Value - left.Value + 1, CV_16U, 0.0);

		cv::Rect floatRegion, baseRegion, commonStitchedRegion,
				commonBaseRegion, commonFloatRegion;

		floatRegion.width = warpedImage.cols;
		floatRegion.height = warpedImage.rows;
		baseRegion.width = image2.cols;
		baseRegion.height = image2.rows;

		if (left.Index == 0) {
			floatRegion.x = 0;
			baseRegion.x = abs(left.Value);
			if (top.Index == 0) {
				floatRegion.y = 0;
				baseRegion.y = abs(top.Value);

				//Common Region
				int commonWidth = cv::min(warpedImage.cols - abs(topLeft.x),
						image2.cols);
				int commonHeight = cv::min(warpedImage.rows - abs(topLeft.y),
						image2.rows);
				commonFloatRegion.width = commonBaseRegion.width =
						commonStitchedRegion.width = commonWidth;
				commonFloatRegion.height = commonBaseRegion.height =
						commonStitchedRegion.height = commonHeight;

				commonFloatRegion.x = abs(topLeft.x);
				commonFloatRegion.y = abs(topLeft.y);

				commonBaseRegion.x = 0;
				commonBaseRegion.y = 0;

				commonStitchedRegion.x = abs(topLeft.x);
				commonStitchedRegion.y = abs(topLeft.y);
			} else {
				floatRegion.y = topLeft.y;
				baseRegion.y = 0;

				//Common Region
				int commonWidth = cv::min(warpedImage.cols - abs(topLeft.x),
						image2.cols);
				int commonHeight = cv::min(warpedImage.rows,
						image2.rows - topLeft.y);

				commonFloatRegion.width = commonBaseRegion.width =
						commonStitchedRegion.width = commonWidth;
				commonFloatRegion.height = commonBaseRegion.height =
						commonStitchedRegion.height = commonHeight;

				commonFloatRegion.x = abs(topLeft.x);
				commonFloatRegion.y = 0;

				commonBaseRegion.x = 0;
				commonBaseRegion.y = abs(topLeft.y);

				commonStitchedRegion.x = abs(topLeft.x);
				commonStitchedRegion.y = topLeft.y;
			}
		} else {
			floatRegion.x = topLeft.x;
			baseRegion.x = 0;
			if (top.Index == 0) {
				floatRegion.y = 0;
				baseRegion.y = abs(top.Value);
				//Common Region
				int commonWidth = cv::min(warpedImage.cols,
						image2.cols - topLeft.x);
				int commonHeight = cv::min(warpedImage.rows - abs(topLeft.y),
						image2.rows);

				commonFloatRegion.width = commonBaseRegion.width =
						commonStitchedRegion.width = commonWidth;
				commonFloatRegion.height = commonBaseRegion.height =
						commonStitchedRegion.height = commonHeight;

				commonFloatRegion.x = 0;
				commonFloatRegion.y = abs(topLeft.y);

				commonBaseRegion.x = topLeft.x;
				commonBaseRegion.y = 0;

				commonStitchedRegion.x = topLeft.x;
				commonStitchedRegion.y = abs(topLeft.y);

			} else {
				floatRegion.y = topLeft.y;
				baseRegion.y = 0;

				//Common Region
				int commonWidth = cv::min(warpedImage.cols,
						image2.cols - topLeft.x);
				int commonHeight = cv::min(warpedImage.rows,
						image2.rows - topLeft.y);

				commonFloatRegion.width = commonBaseRegion.width =
						commonStitchedRegion.width = commonWidth;
				commonFloatRegion.height = commonBaseRegion.height =
						commonStitchedRegion.height = commonHeight;

				commonFloatRegion.x = 0;
				commonFloatRegion.y = 0;

				commonBaseRegion.x = topLeft.x;
				commonBaseRegion.y = topLeft.y;

				commonStitchedRegion.x = topLeft.x;
				commonStitchedRegion.y = topLeft.y;
			}
		}
		warpedImage.copyTo(stitchedImage(floatRegion));
		image2.copyTo(stitchedImage(baseRegion));
		//cv::imwrite("result/blending/raw-composite-image.png",stitchedImage);

		cv::imwrite("result/o_common_base.png", image2(commonBaseRegion));
		cv::imwrite("result/o_common_float.png",
				warpedImage(commonFloatRegion));

		cv::Mat blendedRegion;//=cv::Mat(commonFloatRegion.height,commonFloatRegion.width,CV_8U);

		Neighbor neighbor;
		if (direction == 0) {
			neighbor.Left = FLOAT;
			neighbor.Right = BASE;
			neighbor.Top = NONE;
			neighbor.Bottom = NONE;
		} else if (direction == 1) {
			neighbor.Top = FLOAT;
			neighbor.Bottom = BASE;
			neighbor.Left = NONE;
			neighbor.Right = NONE;
		} else {
			neighbor.Left =
					left.Index == 0 ?FLOAT :BASE;
			neighbor.Top = top.Index == 0 ? FLOAT : BASE;
			neighbor.Right =
					right.Index == 0 ? FLOAT : BASE;
			neighbor.Bottom =
					bottom.Index == 0 ? FLOAT : BASE;
		}

		cv::Mat first=warpedImage(commonFloatRegion);
		cv::Mat second=image2(commonBaseRegion);

		float alphaTime = performAlphaBlend(first,second, neighbor,blendedRegion);

		/*cv::Mat reference;
		 stitchedImage(commonStitchedRegion).copyTo(reference);
		 cv::Mat alphaDiff;
		 cv::absdiff(reference,blendedRegion,alphaDiff);
		 cv::imwrite("result/blending/Diff_Alpha.png",alphaDiff);*/
		blendedRegion.copyTo(stitchedImage(commonStitchedRegion));
		//cv::imshow("StitchedImage",stitchedImage);
		//cv::waitKey(0);
		return stitchedImage;
		/*float laplacianTime=performLaplacianBlend(warpedImage(commonFloatRegion),image2(commonBaseRegion),blendedRegion);
		 cv::Mat pyramidDifference;
		 cv::absdiff(reference,blendedRegion,pyramidDifference);
		 cv::imwrite("result/blending/Diff_Pyramid.png",pyramidDifference);
		 blendedRegion.copyTo(stitchedImage(commonStitchedRegion));
		 cv::imwrite("result/blending/StitchedImage(Laplacian).png",stitchedImage);

		 log.Write(resultFile,"Alpha Blending Took %f seconds",alphaTime);
		 log.Write(resultFile,"Pyramid Blending Took %f seconds",laplacianTime);

		 float alphaError=0.0,laplaceError=0.0;

		 for(int i=0;i<alphaDiff.rows;i++){
		 for(int j=0;j<alphaDiff.cols;j++){
		 alphaError+=alphaDiff.at<uchar>(i,j)/255.0;
		 laplaceError+=pyramidDifference.at<uchar>(i,j)/255.0;
		 }
		 }

		 log.Write(resultFile,"Alpha Blending Inaccuracy=%f ",alphaError);
		 log.Write(resultFile,"Pyramid Bleidng Inaccuracy=%f",laplaceError);*/
	}

	void rotateImage(const cv::Mat image, cv::Mat homography,
			cv::Mat& outputImage, cv::Point& topLeft, cv::Point& bottomRight) {
		cv::Mat corners1(1, 4, CV_32F);
		cv::Mat corners2(1, 4, CV_32F);
		cv::Mat corners(1, 4, CV_32F);
		cv::vector<cv::Mat> planes;
		corners1.at<float>(0, 0) = 0;
		corners2.at<float>(0, 0) = 0;
		corners1.at<float>(0, 1) = image.cols;
		corners2.at<float>(0, 1) = 0;
		corners1.at<float>(0, 2) = image.cols;
		corners2.at<float>(0, 2) = image.rows;
		corners1.at<float>(0, 3) = 0;
		corners2.at<float>(0, 3) = image.rows;
		planes.push_back(corners1);
		planes.push_back(corners2);

		cv::merge(planes, corners);

		cv::perspectiveTransform(corners, corners, homography);

		getCorners(corners, topLeft, bottomRight);

		cv::Mat T;
		T = cv::Mat::zeros(3, 3, CV_64F);
		T.at<double>(0, 0) = 1;
		T.at<double>(1, 1) = 1;
		T.at<double>(2, 2) = 1;
		T.at<double>(0, 2) = -topLeft.x;
		T.at<double>(1, 2) = -topLeft.y;

		// change homography to take necessary translation into account
		cv::gemm(T, homography, 1, T, 0, T);
		// warp second image and copy it to output image
		cv::warpPerspective(image, outputImage, T,
				cv::Size(bottomRight.x - topLeft.x, bottomRight.y - topLeft.y),
				cv::INTER_NEAREST, 0, 0);

	}

	cv::Mat calculateHomography() {
		//resizeImages();
		cv::Mat image1_8bit, image2_8bit;
		this->image1.convertTo(image1_8bit, CV_8U, 1. / 256);
		this->image2.convertTo(image2_8bit, CV_8U, 1. / 256);
		cv::Mat cropped1;
		cv::Mat cropped2;
		//getCroppedImages(image1_8bit,image2_8bit,cropped1,cropped2);
		cropped1 = image1_8bit.clone();
		cropped2 = image2_8bit.clone();
		/*cv::medianBlur(image1_8bit,image1_8bit,3);
		 cv::medianBlur(image2_8bit,image2_8bit,3);*/
		std::vector<cv::KeyPoint> keyPoints1;
		std::vector<cv::KeyPoint> keyPoints2;
		cv::Mat descriptor1, descriptor2;

		cv::Ptr<cv::FeatureDetector> detector = new cv::SurfFeatureDetector(0.5,
				4, 2, true, false);
		detector->detect(cropped1, keyPoints1);
		//detector=new cv::SurfFeatureDetector(100);
		//detector=new cv::SurfFeatureDetector(400);
		detector->detect(cropped2, keyPoints2);

		cv::Ptr<cv::DescriptorExtractor> extractor =
				new cv::SurfDescriptorExtractor(0.0005, 4, 2, true, false);
		extractor->compute(cropped1, keyPoints1, descriptor1);
		extractor->compute(cropped2, keyPoints2, descriptor2);

		//DRAW KEYPOINTS
		cv::Mat tmpImage;
		/*
		 cv::drawKeypoints(cropped1,keyPoints1,tmpImage);
		 cv::imwrite("output/o_Image1(keyPoints).bmp",tmpImage);
		 cv::drawKeypoints(cropped2,keyPoints2,tmpImage);
		 cv::imwrite("output/o_Image2(keyPoints).bmp",tmpImage);*/

		/*
		 std::vector<cv::DMatch> flannSymmetryMatches;
		 cv::FlannBasedMatcher flannBasedMatcher;
		 std::vector<std::vector<cv::DMatch>> flannMatches1, flannMatches2;

		 flannBasedMatcher.knnMatch(descriptor1,descriptor2,flannMatches1,2);
		 flannBasedMatcher.knnMatch(descriptor2, descriptor1,flannMatches2,2);

		 RatioTest(flannMatches1,0.8);
		 RatioTest(flannMatches2,0.8);
		 SymmetryTest(flannMatches1,flannMatches2,flannSymmetryMatches);
		 */

		std::vector<cv::DMatch> bruteForceSymmetryMatches;
		//cv::FlannBasedMatcher bruteForceBasedMatcher;		
		cv::BruteForceMatcher<cv::L2<float> > bruteForceBasedMatcher;

		std::vector<std::vector<cv::DMatch> > bruteForceMatches1,
				bruteForceMatches2;

		bruteForceBasedMatcher.knnMatch(descriptor1, descriptor2,
				bruteForceMatches1, 2);
		bruteForceBasedMatcher.knnMatch(descriptor2, descriptor1,
				bruteForceMatches2, 2);

		int removed1 = RatioTest(bruteForceMatches1, 0.7);
		int removed2 = RatioTest(bruteForceMatches2, 0.7);

		int symmetryMatchesCount = SymmetryTest(bruteForceMatches1,
				bruteForceMatches2, bruteForceSymmetryMatches);
		cv::drawMatches(cropped1, keyPoints1, cropped2, keyPoints2,
				bruteForceSymmetryMatches, tmpImage);
		cv::imwrite("output/o_SymmetryMatches.bmp", tmpImage);

		std::vector<cv::Point2f> points1, points2;
		std::vector<uchar> inliers;
		GetFloatPoints(keyPoints1, keyPoints2, bruteForceSymmetryMatches,
				points1, points2);

		/**
		 AFFINE 3D
		 **/
		//cv::vector<uchar> inliersAffine;
		//cv::Mat affine;
		//cv::estimateAffine3D(cv::Mat(points1),cv::Mat(points2),affine,inliersAffine);
		//cv::Mat affine=cv::getAffineTransform(cv::Mat(points1),cv::Mat(points2));
		//cv::getRigi
		cv::Mat homography = cv::findHomography(cv::Mat(points1), //Corresponding
		cv::Mat(points2), //matching points
		inliers,          //inliers
		//CV_LMEDS,
		CV_RANSAC,        //using RANSAC method
		distanceThreshold);        //maximum pixel distance
		/*
		 printf("\nHomography=");
		 for(int i=0;i<3;i++){
		 for(int j=0;j<3;j++){
		 printf("%f\t",homography.at<double>(i,j));
		 }
		 printf("\n");
		 }*/

		//DRAWING INLIERS
		std::vector<cv::DMatch>::const_iterator symmetryIterator =
				bruteForceSymmetryMatches.begin();
		std::vector<uchar>::const_iterator inliersIterator = inliers.begin();

		std::vector<cv::DMatch> inlierMatches;
		int inliersCount=0;
		for (; symmetryIterator != bruteForceSymmetryMatches.end();
				symmetryIterator++, inliersIterator++) {
			if (*inliersIterator) {
				inlierMatches.push_back(*symmetryIterator);
				inliersCount++;
			}
		}
		cv::drawMatches(cropped1, keyPoints1, cropped2, keyPoints2,
				inlierMatches, tmpImage);
		cv::imwrite("output/inliers.png", tmpImage);

		/*std::vector<cv::Point2f> affine1, affine2;
		 affine1.push_back(keyPoints1.at(inlierMatches.at(0).queryIdx).pt);
		 affine2.push_back(keyPoints2.at(inlierMatches.at(0).trainIdx).pt);
		 affine1.push_back(keyPoints1.at(inlierMatches.at(1).queryIdx).pt);
		 affine2.push_back(keyPoints2.at(inlierMatches.at(1).trainIdx).pt);
		 affine1.push_back(keyPoints1.at(inlierMatches.at(2).queryIdx).pt);
		 affine2.push_back(keyPoints2.at(inlierMatches.at(2).trainIdx).pt);
		 /*affine1.push_back(keyPoints1.at(inlierMatches.at(3).queryIdx).pt);
		 affine2.push_back(keyPoints2.at(inlierMatches.at(3).trainIdx).pt);*/
		/*
		 cv::Mat affine=cv::getAffineTransform(affine1,affine2);

		 printf("\nAffine=");
		 for(int i=0;i<2;i++){
		 for(int j=0;j<3;j++){
		 printf("%f\t",affine.at<double>(i,j));
		 }
		 printf("\n");
		 }

		 homography.at<double>(2,2) = 1;
		 homography.at<double>(2,0) = homography.at<double>(2,1) = 0;
		 */

		//double theta=atan2(homography.at<double>(0,1),homography.at<double>(0,0));
		/*
		 homography.at<double>(0,0)=affine.at<double>(0,0);
		 homography.at<double>(0,1)=affine.at<double>(0,1);
		 homography.at<double>(1,0)=affine.at<double>(1,0);
		 homography.at<double>(1,1)=affine.at<double>(1,1);
		 homography.at<double>(0,2)=affine.at<double>(0,2);
		 homography.at<double>(1,2)=affine.at<double>(1,2);
		 */
		if(inliersCount>10){
			return homography;
		}
		else{
			return cv::Mat::zeros(1,1,CV_64F);
		}
	}

	void getCroppedImages(cv::Mat image1_8bit, cv::Mat image2_8bit,
			cv::Mat& cropped1, cv::Mat& cropped2) {
		std::vector<cv::KeyPoint> keyPoints1;
		std::vector<cv::KeyPoint> keyPoints2;
		cv::Mat descriptor1, descriptor2;

		cv::Ptr<cv::FeatureDetector> detector = new cv::SurfFeatureDetector(1,
				4, 2, true, false);
		detector->detect(image1_8bit, keyPoints1);
		//detector=new cv::SurfFeatureDetector(100);
		//detector=new cv::SurfFeatureDetector(400);
		detector->detect(image2_8bit, keyPoints2);

		cv::Ptr<cv::DescriptorExtractor> extractor =
				new cv::SurfDescriptorExtractor(0.1, 4, 2, true, false);
		extractor->compute(image1_8bit, keyPoints1, descriptor1);
		extractor->compute(image2_8bit, keyPoints2, descriptor2);

		//DRAW KEYPOINTS
		cv::Mat tmpImage;
		cv::drawKeypoints(image1_8bit, keyPoints1, tmpImage);
		cv::imwrite("output/o_cImage1(keyPoints).bmp", tmpImage);
		cv::drawKeypoints(image2_8bit, keyPoints2, tmpImage);
		cv::imwrite("output/o_cImage2(keyPoints).bmp", tmpImage);

		/*
		 std::vector<cv::DMatch> flannSymmetryMatches;
		 cv::FlannBasedMatcher flannBasedMatcher;
		 std::vector<std::vector<cv::DMatch>> flannMatches1, flannMatches2;

		 flannBasedMatcher.knnMatch(descriptor1,descriptor2,flannMatches1,2);
		 flannBasedMatcher.knnMatch(descriptor2, descriptor1,flannMatches2,2);

		 RatioTest(flannMatches1,0.8);
		 RatioTest(flannMatches2,0.8);
		 SymmetryTest(flannMatches1,flannMatches2,flannSymmetryMatches);
		 */

		std::vector<cv::DMatch> bruteForceSymmetryMatches;
		//cv::FlannBasedMatcher bruteForceBasedMatcher;		
		cv::BruteForceMatcher<cv::L2<float> > bruteForceBasedMatcher;

		std::vector<std::vector<cv::DMatch> > bruteForceMatches1,
				bruteForceMatches2;

		bruteForceBasedMatcher.knnMatch(descriptor1, descriptor2,
				bruteForceMatches1, 2);
		bruteForceBasedMatcher.knnMatch(descriptor2, descriptor1,
				bruteForceMatches2, 2);

		int removed1 = RatioTest(bruteForceMatches1, 0.7);
		int removed2 = RatioTest(bruteForceMatches2, 0.7);

		int symmetryMatchesCount = SymmetryTest(bruteForceMatches1,
				bruteForceMatches2, bruteForceSymmetryMatches);
		cv::drawMatches(image1_8bit, keyPoints1, image2_8bit, keyPoints2,
				bruteForceSymmetryMatches, tmpImage);
		cv::imwrite("output/o_cSymmetryMatches.bmp", tmpImage);

		std::vector<cv::Point2f> points1, points2;
		std::vector<uchar> inliers;
		GetFloatPoints(keyPoints1, keyPoints2, bruteForceSymmetryMatches,
				points1, points2);

		cv::Mat homography = cv::findHomography(cv::Mat(points1), //Corresponding
		cv::Mat(points2), //matching points
		inliers,          //inliers
				CV_RANSAC,        //using RANSAC method
				distanceThreshold);        //maximum pixel distance

		//DRAWING INLIERS
		std::vector<cv::DMatch>::const_iterator symmetryIterator =
				bruteForceSymmetryMatches.begin();
		std::vector<uchar>::const_iterator inliersIterator = inliers.begin();

		std::vector<cv::DMatch> inlierMatches;
		for (; symmetryIterator != bruteForceSymmetryMatches.end();
				symmetryIterator++, inliersIterator++) {
			if (*inliersIterator) {
				inlierMatches.push_back(*symmetryIterator);
			}
		}

		cv::drawMatches(image1_8bit, keyPoints1, image2_8bit, keyPoints2,
				inlierMatches, tmpImage);
		cv::imwrite("output/c_inliers.png", tmpImage);

		int minX1 = image1_8bit.size().width, minY1 = image1_8bit.size().height;
		int minX2 = image2_8bit.size().width, minY2 = image2_8bit.size().height;
		int maxX1 = 0, maxY1 = 0;
		int maxX2 = 0, maxY2 = 0;

		for (int i = 0; i < inlierMatches.size(); i++) {
			printf("%d", i);
			int x1 = keyPoints1.at(inlierMatches.at(i).queryIdx).pt.x;
			int x2 = keyPoints2.at(inlierMatches.at(i).trainIdx).pt.x;
			int y1 = keyPoints1.at(inlierMatches.at(i).queryIdx).pt.y;
			int y2 = keyPoints2.at(inlierMatches.at(i).trainIdx).pt.y;
			if (x1 < minX1)
				minX1 = x1;
			if (x1 > maxX1)
				maxX1 = x1;
			if (x2 < minX2)
				minX2 = x2;
			if (x2 > maxX2)
				maxX2 = x2;

			if (y1 < minY1)
				minY1 = y1;
			if (y1 > maxY1)
				maxY1 = y1;
			if (y2 < minY2)
				minY2 = y2;
			if (y2 > maxY2)
				maxY2 = y2;
		}

		minX1 = minX1 - 10 < 0 ? 0 : minX1 - 10;
		maxX1 = maxX1 + 10 > image1_8bit.size().width - 1 ?
				image1_8bit.size().width - 1 : maxX1 + 10;
		minX2 = minX2 - 10 < 0 ? 0 : minX2 - 10;
		maxX2 = maxX2 + 10 > image2_8bit.size().width - 1 ?
				image2_8bit.size().width - 1 : maxX2 + 10;

		minY1 = minY1 - 10 < 0 ? 0 : minY1 - 10;
		maxY1 = maxY1 + 10 > image1_8bit.size().height - 1 ?
				image1_8bit.size().height - 1 : maxY1 + 10;
		minY2 = minY2 - 10 < 0 ? 0 : minY2 - 10;
		maxY2 = maxY2 + 10 > image2_8bit.size().height - 1 ?
				image2_8bit.size().height - 1 : maxY2 + 10;

		cropped1.create(image1_8bit.size().height, image1_8bit.size().width,
				CV_8U);
		cropped2.create(image2_8bit.size().height, image2_8bit.size().width,
				CV_8U);

		image1_8bit(cv::Rect(minX1, minY1, maxX1 - minX1, maxY1 - minY1)).copyTo(
				cropped1(cv::Rect(minX1, minY1, maxX1 - minX1, maxY1 - minY1)));
		image2_8bit(cv::Rect(minX2, minY2, maxX2 - minX2, maxY2 - minY2)).copyTo(
				cropped2(cv::Rect(minX2, minY2, maxX2 - minX2, maxY2 - minY2)));

		cv::imwrite("output/o_cropped1.png", cropped1);
		cv::imwrite("output/o_cropped2.png", cropped2);
	}

	void resizeImages() {
		cv::Mat image1_resized,image2_resized;
		int rows = 0;
		int columns = 0;
		float scale = 1;

		if (getImageArea(image1) > getImageArea(image2)) {
			rows = image1.rows;
			columns = image1.cols;
			int count = 0;
			float factor = 0.5;
			do {
				if (rows * scale * columns * scale > limitPixels) {
					scale -= scale * factor;
				} else {
					scale += factor * scale;
				}
				factor *= 0.5;
				count++;
			} while (count < 100);
			printf("%f", scale);
		} else {
			rows = image2.rows;
			columns = image2.cols;
			int count = 0;
			float factor = 0.5;
			do {
				if (rows * scale * columns * scale > limitPixels) {
					scale -= scale * factor;
				} else {
					scale += factor * scale;
				}
				factor *= 0.5;
				count++;
				printf("%f\n", scale);
			} while (count < 100);
			printf("%f\n", scale);
			printf("%f", rows * scale * columns * scale);
		}
		cv::resize(image1, image1_resized,
				cv::Size((int) image1.cols * scale, (int) image1.rows * scale));
		cv::resize(image2, image2_resized,
				cv::Size((int) image2.cols * scale, (int) image2.rows * scale));
		image1 = image1_resized.clone();
		image2 = image2_resized.clone();
	}

	void getCorners(const cv::Mat corners, cv::Point& topLeft,
			cv::Point& bottomRight) {
		topLeft.x = cv::min(
				cv::min((double) corners.at<cv::Vec2f>(0, 0)[0],
						(double) corners.at<cv::Vec2f>(0, 1)[0]),
				cv::min((double) corners.at<cv::Vec2f>(0, 2)[0],
						(double) corners.at<cv::Vec2f>(0, 3)[0]));
		topLeft.y = cv::min(
				cv::min((double) corners.at<cv::Vec2f>(0, 0)[1],
						(double) corners.at<cv::Vec2f>(0, 1)[1]),
				cv::min((double) corners.at<cv::Vec2f>(0, 2)[1],
						(double) corners.at<cv::Vec2f>(0, 3)[1]));

		bottomRight.x = cv::max(
				cv::max((double) corners.at<cv::Vec2f>(0, 0)[0],
						(double) corners.at<cv::Vec2f>(0, 1)[0]),
				cv::max((double) corners.at<cv::Vec2f>(0, 2)[0],
						(double) corners.at<cv::Vec2f>(0, 3)[0]));
		bottomRight.y = cv::max(
				cv::max((double) corners.at<cv::Vec2f>(0, 0)[1],
						(double) corners.at<cv::Vec2f>(0, 1)[1]),
				cv::max((double) corners.at<cv::Vec2f>(0, 2)[1],
						(double) corners.at<cv::Vec2f>(0, 3)[1]));
	}

	int SymmetryTest(const std::vector<std::vector<cv::DMatch> >& matches1,
			const std::vector<std::vector<cv::DMatch> >& matches2,
			std::vector<cv::DMatch>& symMatches) {
		int64 tick = cv::getTickCount();
		//check for image1->imag2 matches
		for (std::vector<std::vector<cv::DMatch> >::const_iterator matchIterator1 =
				matches1.begin(); matchIterator1 != matches1.end();
				++matchIterator1) {
			if (matchIterator1->size() < 2)
				continue;
			//check for image2->image1 matches
			for (std::vector<std::vector<cv::DMatch> >::const_iterator matchIterator2 =
					matches2.begin(); matchIterator2 != matches2.end();
					++matchIterator2) {
				if (matchIterator2->size() < 2)
					continue;
				//Match Symmetry Test
				bool condition = (*matchIterator1)[0].queryIdx
						== (*matchIterator2)[0].trainIdx
						&& (*matchIterator1)[0].trainIdx
								== (*matchIterator2)[0].queryIdx;
				if (condition) {
					symMatches.push_back(
							cv::DMatch((*matchIterator1)[0].queryIdx,
									(*matchIterator1)[0].trainIdx,
									(*matchIterator1)[0].distance));
					break;
				}
			}
		}
		//printf("Symmetry matches removed=%d points",matches1.size()-symMatches.size());
		//printf("Symmetry Test Took %f Seconds",(cv::getTickCount()-tick)/cv::getTickFrequency());
		return symMatches.size();
	}

//int SymmetryTest_Flann(const std::vector<cv::DMatch>& matches1,
//	const std::vector<cv::DMatch>& matches2,
//	std::vector<cv::DMatch>& symMatches){
//		int64 tick=cv::getTickCount();
//		for(std::vector<cv::DMatch>::const_iterator matchIterator1=matches1.begin();
//			matchIterator1!=matches1.end();++matchIterator1){
//				for(std::vector<cv::DMatch>::const_iterator matchIterator2=matches2.begin();
//					matchIterator2!=matches2.end();++matchIterator2){
//						bool condition=(*matchIterator1).queryIdx==(*matchIterator2).trainIdx
//							&&(*matchIterator1).trainIdx==(*matchIterator2).queryIdx;
//							if(condition){
//								symMatches.push_back(cv::DMatch((*matchIterator1).queryIdx,
//									(*matchIterator1).trainIdx,(*matchIterator1).distance));								
//								break;
//							}
//				}
//		}
//		printf("Flann Symmetry matches removed=%d points",matches1.size()-symMatches.size());
//		printf("Flann Symmetry Test Took %f Seconds",(cv::getTickCount()-tick)/cv::getTickFrequency());
//}

	int RatioTest(std::vector<std::vector<cv::DMatch> >& matches,
			double threshold) {
		int removed = 0;
		for (std::vector<std::vector<cv::DMatch> >::iterator matchIterator =
				matches.begin(); matchIterator != matches.end();
				++matchIterator) {
			if (matchIterator->size() > 1) {
				if ((*matchIterator)[0].distance / (*matchIterator)[1].distance
						> threshold) {
					matchIterator->clear();
					removed++;
				}
			} else {
				matchIterator->clear();
				removed++;
			}
		}
		return removed;
	}

	void GetFloatPoints(const std::vector<cv::KeyPoint>& keyPoints1,
			const std::vector<cv::KeyPoint>& keyPoints2,
			const std::vector<cv::DMatch>& matches,
			std::vector<cv::Point2f>& points1,
			std::vector<cv::Point2f>& points2) {
		for (std::vector<cv::DMatch>::const_iterator iterator = matches.begin();
				iterator != matches.end(); ++iterator) {
			float x = keyPoints1[iterator->queryIdx].pt.x;
			float y = keyPoints1[iterator->queryIdx].pt.y;
			points1.push_back(cv::Point2f(x, y));
			x = keyPoints2[iterator->trainIdx].pt.x;
			y = keyPoints2[iterator->trainIdx].pt.y;
			points2.push_back(cv::Point2f(x, y));
		}
	}

	float performAlphaBlend(cv::Mat& image1, cv::Mat& image2,
			Neighbor neighbor, cv::Mat& outputImage) {
		int64 tick = cv::getTickCount();
		cv::Mat_<float> blendedImage[5];
		for (int i = 0; i < 5; i++) {
			blendedImage[i].create(image1.rows, image1.cols);
			//cv::imshow("BlendedImage",blendedImage[i]);
			//cv::waitKey(0);
		}

		cv::Mat image1Clone = image1.clone();
		cv::Mat image2Clone = image2.clone();

		this->levelPixels(image1Clone, image2Clone);

		cv::Mat_<float> image1F, image2F;
		image1Clone.convertTo(image1F, CV_32F, 1.0 / 65535.0);
		image2Clone.convertTo(image2F, CV_32F, 1.0 / 65535.0);

		//BlendMask masks=createHorizontalBlendask(image1.rows,image1.cols);
		BlendMask masks = createBlendMask(image1.rows, image1.cols, neighbor);
		cv::Mat_<float> blendMask[4];
		blendMask[0] = masks.Left;
		blendMask[1] = masks.Top;
		blendMask[2] = masks.Right;
		blendMask[3] = masks.Bottom;

		cv::Mat temp;
		for (int i = 0; i < 4; i++) {
			char* name = (char*) malloc(100 * sizeof(char));
			//std::sprintf(name,"output/blending-mask-%d.png",i);
			blendMask[i].convertTo(temp, CV_8U, 255);
			//cv::imwrite("test", temp);
			/*cv::imshow("Mask",blendMask[i]);
			 cv::waitKey(0);*/
		}

		if (neighbor.Left == FLOAT) {
			blendedImage[0] = blendMask[0].mul(image1F);
		} else if (neighbor.Left == BASE) {
			blendedImage[0] = blendMask[0].mul(image2F);
		}

		if (neighbor.Top == FLOAT) {
			blendedImage[1] = blendMask[1].mul(image1F);
		} else if (neighbor.Top == BASE) {
			blendedImage[1] = blendMask[1].mul(image2F);
		}

		if (neighbor.Right == FLOAT) {
			blendedImage[2] = blendMask[2].mul(image1F);
		} else if (neighbor.Right == BASE) {
			blendedImage[2] = blendMask[2].mul(image2F);
		}

		if (neighbor.Bottom == FLOAT) {
			blendedImage[3] = blendMask[3].mul(image1F);
		} else if (neighbor.Bottom == BASE) {
			blendedImage[3] = blendMask[3].mul(image2F);
		}

		blendedImage[4] = blendedImage[0] + blendedImage[1] + blendedImage[2]
				+ blendedImage[3];

//		for (int i = 0; i < 5; i++) {
//			//cv::imshow("Blended", blendedImage[i]);
//			//cv::waitKey(0);
//		}

		//cout<<blendedImage[4];
		blendedImage[4].convertTo(outputImage, CV_16U, 65535);

		float elapsedTime = (cv::getTickCount() - tick)
				/ cv::getTickFrequency();

		cv::imwrite("output/alpha_blend.png", outputImage);
		return elapsedTime;
	}

	void levelPixels(cv::Mat& image1, cv::Mat& image2) {
		//int diff=image1.at<ushort>(image1.rows/2,image1.cols/2)-image2.at<ushort>(image2.rows/2,image2.cols/2);
		for (int i = 0; i < image1.rows; i++) {
			for (int j = 0; j < image1.cols; j++) {
				if (image1.at<ushort>(i, j) == 0) {
					image1.at<ushort>(i, j) = image2.at<ushort>(i, j);	//+diff;
				}
				if (image2.at<ushort>(i, j) == 0) {
					image2.at<ushort>(i, j) = image1.at<ushort>(i, j);	//-diff;
				}
				/*if(image1.at<ushort>(i,j)!=0 && image2.at<ushort>(i,j)==0){
				 diff=image1.at<ushort>(i,j)-image2.at<ushort>(i,j);
				 }*/
			}
		}
		/*cv::medianBlur(image1,image1,3);
		 cv::medianBlur(image2,image2,3);*/
		cv::imwrite("output/o_Levelling1.png", image1);
		cv::imwrite("output/o_Levelling2.png", image2);
	}

	/*
	 #pragma region Pyramid Blending
	 float performLaplacianBlend(const cv::Mat& top, const cv::Mat& bottom, cv::Mat& blendedImage){
	 int64 tick=cv::getTickCount();

	 cv::Mat_<cv::Vec3f> t,b;
	 cv::Vector<cv::Mat_<cv::Vec3f>> topLapPyr, bottomLapPyr, resultPyr;

	 //Smallest Images
	 cv::Mat_<cv::Vec3f> topSmallestLevel, bottomSmallestLevel,resultSmallestLevel;
	 //Mask Gaussian Pyramid
	 cv::Vector<cv::Mat_<cv::Vec3f>> maskGaussianPyramid;


	 top.convertTo(t,CV_32F,1.0/255.0);
	 bottom.convertTo(b,CV_32F,1.0/255.0);


	 cv::cvtColor(t,t,CV_GRAY2BGR);
	 cv::cvtColor(b,b,CV_GRAY2BGR);


	 generateLaplacianPyramid(t,topLapPyr,topSmallestLevel);
	 generateLaplacianPyramid(b,bottomLapPyr,bottomSmallestLevel);


	 //Blend Mask
	 BlendMask masks=createHorizontalBlendMask(t.rows,t.cols);

	 cv::Mat_<float> blendMask=masks.Left;

	 /*for(int i=0;i<t.cols;i++){
	 for(int j=0;j<t.rows;j++){
	 if(i==0 && j==0){
	 blendMask.at<float>(j,i)=1.0;
	 continue;
	 }
	 int shortX=std::min(i,(t.cols-i));
	 int shortY=std::min(j,(t.rows-j));
	 blendMask.at<float>(j,i)=1.0-(float)shortX/(shortX+shortY);
	 }
	 }*/

	/*

	 generateGaussianPyramid(blendMask, topLapPyr, topSmallestLevel, maskGaussianPyramid);

	 /*cv::Mat temp;
	 cv::cvtColor(topSmallestLevel,temp,CV_RGB2GRAY);
	 temp.convertTo(temp,CV_8U,255.0);*/
	//cv::imwrite("result/blending/top_smallest.png",temp);
	/*cv::cvtColor(bottomSmallestLevel,temp,CV_RGB2GRAY);
	 temp.convertTo(temp,CV_8U,255.0);*/
	//cv::imwrite("result/blending/bottom_smallest.png",temp);

	//for(int i=0;i<level;i++){
	//	char name[100];
	//sprintf(name,"result/blending/pyramids/gaussian/gaussian_pyramids_%d.png",i);
	//cv::Mat temp1,temp2;
	//cv::cvtColor(maskGaussianPyramid[i],temp1,CV_RGB2GRAY);
	//temp1.convertTo(temp2,CV_8U,255.0);
	//cv::imwrite(name,temp2);
	//sprintf(name,"result/blending/pyramids/laplacian/image1/lap_pyramids_%d.png",i);
	//	cv::cvtColor(topLapPyr[i],temp1,CV_RGB2GRAY);
	//	temp1.convertTo(temp2,CV_8U,255.0);
	//	cv::imwrite(name,temp2);

	//sprintf(name,"result/blending/pyramids/laplacian/image2/lap_pyramids_%d.png",i);
	//cv::cvtColor(bottomLapPyr[i],temp1,CV_RGB2GRAY);
	//temp1.convertTo(temp2,CV_8U,255.0);
	//cv::imwrite(name,temp2);
	//}
	//generateGaussianPyramid(blendMask, bottomLapPyr, bottomSmallestLevel, bottomMaskGaussianPyramid);
	/*
	 blendLapPyrs(topLapPyr,bottomLapPyr,topSmallestLevel,bottomSmallestLevel,blendMask,maskGaussianPyramid,resultSmallestLevel,resultPyr);
	 cv::Mat blendedImage_32=reconstructImage(resultPyr,resultSmallestLevel);
	 cv::cvtColor(blendedImage_32,blendedImage_32,CV_BGR2GRAY);
	 blendedImage_32.convertTo(blendedImage,CV_8U,255);

	 float elapsedTime=(cv::getTickCount()-tick)/cv::getTickFrequency();
	 cv::imwrite("result/blending/lap_pyr_blend.png",blendedImage);
	 return elapsedTime;

	 }*/

	/*
	 void generateLaplacianPyramid(const cv::Mat_<cv::Vec3f>& image,
	 cv::Vector<cv::Mat_<cv::Vec3f>>& lapPyr,
	 cv::Mat_<cv::Vec3f>& smallestLevel){
	 lapPyr.clear();
	 cv::Mat_<cv::Vec3f> currentImage=image.clone();
	 for(int i=0;i<level;i++){
	 cv::Mat_<cv::Vec3f> down, up;
	 cv::pyrDown(currentImage,down);
	 cv::pyrUp(down, up,currentImage.size());
	 cv::Mat_<cv::Vec3f> lap;
	 cv::absdiff(currentImage,up,lap);
	 lapPyr.push_back(lap);
	 currentImage=down;
	 }
	 currentImage.copyTo(smallestLevel);
	 }

	 void generateGaussianPyramid(cv::Mat& blendMask,cv::Vector<cv::Mat_<cv::Vec3f>> lapPyr,cv::Mat smallestLevel,
	 cv::Vector<cv::Mat_<cv::Vec3f>>& maskGaussianPyramid){
	 assert(lapPyr.size()>0);

	 maskGaussianPyramid.clear();

	 cv::Mat currentImage;

	 cv::cvtColor(blendMask,currentImage,CV_GRAY2BGR);
	 maskGaussianPyramid.push_back(currentImage);

	 currentImage=blendMask;

	 for(int i=1;i<level+1;i++){
	 cv::Mat _down;
	 if(i<lapPyr.size()){
	 cv::pyrDown(currentImage,_down,lapPyr[i].size());
	 }else{
	 cv::pyrDown(currentImage,_down,smallestLevel.size());
	 }
	 /*char message[100];
	 sprintf(message,"Gaussian Mask: Level %d",i);
	 cv::Mat down;
	 cv::cvtColor(_down,down,CV_GRAY2BGR);
	 maskGaussianPyramid.push_back(down);
	 currentImage=_down;
	 }
	 }

	 void blendLapPyrs(cv::Vector<cv::Mat_<cv::Vec3f>>& lapPyr1,
	 cv::Vector<cv::Mat_<cv::Vec3f>>& lapPyr2,
	 cv::Mat_<cv::Vec3f>& smallestLevel1,cv::Mat_<cv::Vec3f>& smallestLevel2,
	 cv::Mat_<float>& blendMask,
	 cv::Vector<cv::Mat_<cv::Vec3f>> maskGaussianPyramid,
	 cv::Mat& resultSmallestLevel,cv::Vector<cv::Mat_<cv::Vec3f>>& resultPyr){
	 /*printf("Smallest Level1 Size:row=%d, column=%d\t Mask gauss. Size row=%d col=%d",
	 smallestLevel1.rows,smallestLevel1.cols,maskGaussianPyramid.back().rows,maskGaussianPyramid.back().cols);
	 printf("Smallest Level2 Size:row=%d, column=%d\t Mask gauss. Size row=%d col=%d",
	 smallestLevel2.rows,smallestLevel2.cols,maskGaussianPyramid.back().rows,maskGaussianPyramid.back().cols);


	 resultSmallestLevel=smallestLevel1.mul(maskGaussianPyramid.back())+smallestLevel2.mul(cv::Scalar(1.0,1.0,1.0)-maskGaussianPyramid.back());

	 for(int i=0;i<level;i++){
	 cv::Mat A=lapPyr1[i].mul(maskGaussianPyramid[i]);
	 cv::Mat antiMask=cv::Scalar(1.0,1.0,1.0)-maskGaussianPyramid[i];
	 cv::Mat B=lapPyr2[i].mul(antiMask);
	 cv::Mat_<cv::Vec3f> result=A+B;
	 resultPyr.push_back(result);
	 }
	 }

	 cv::Mat reconstructImage(cv::Vector<cv::Mat_<cv::Vec3f>> resultPyr,cv::Mat resultSmallestLevel){
	 cv::Mat currentImage=resultSmallestLevel;
	 for(int i=level-1; i>=0;i--){
	 cv::Mat up;
	 cv::pyrUp(currentImage,up,resultPyr[i].size());
	 currentImage=up+resultPyr[i];
	 }
	 return currentImage;
	 }
	 */
#pragma endregion Pyramid Blending Ends

	BlendMask createHorizontalBlendMask(int rows, int cols) {
		BlendMask masks;
		masks.Left = masks.Top = masks.Right = masks.Bottom = cv::Mat_<float>(
				rows, cols, 0.0);
		cv::Mat_<float> blendMask[4];
		blendMask[0] = cv::Mat_<float>(rows, cols, 0.0);
		blendMask[1] = cv::Mat_<float>(rows, cols, 0.0);
		blendMask[2] = cv::Mat_<float>(rows, cols, 0.0);
		blendMask[3] = cv::Mat_<float>(rows, cols, 0.0);

		for (int j = 0; j < cols; j++) {
			float value1 = (float) (cols - j) / cols;
			float value2 = (float) j / cols;
			//printf("value1=%f\tvalue2=%f\n",value1,value2);
			for (int i = 0; i < rows; i++) {
				blendMask[0].at<float>(i, j) = value1;
				blendMask[2].at<float>(i, j) = value2;
			}
		}
		masks.Left = blendMask[0];
		masks.Right = blendMask[2];
		cv::Mat tmpImage;
		blendMask[0].convertTo(tmpImage, CV_8U, 255);
		cv::imwrite("result/blending/LeftMask.png", tmpImage);
		blendMask[2].convertTo(tmpImage, CV_8U, 255);
		cv::imwrite("result/blending/RightMask.png", tmpImage);
		return masks;
	}
	/**
	 Vertical mask
	 **/
	BlendMask createVerticalMask(int rows, int cols) {
		BlendMask masks;
		masks.Left = masks.Top = masks.Right = masks.Bottom = cv::Mat_<float>(
				rows, cols, 0.0);
		cv::Mat_<float> blendMask[4];
		blendMask[0] = cv::Mat_<float>(rows, cols, 0.0);
		blendMask[1] = cv::Mat_<float>(rows, cols, 0.0);
		blendMask[2] = cv::Mat_<float>(rows, cols, 0.0);
		blendMask[3] = cv::Mat_<float>(rows, cols, 0.0);

		for (int j = 0; j < cols; j++) {
			float value1 = (float) (rows - j) / rows;
			float value2 = (float) j / rows;
			//printf("value1=%f\tvalue2=%f\n",value1,value2);
			for (int i = 0; i < cols; i++) {
				blendMask[1].at<float>(i, j) = value1;
				blendMask[3].at<float>(i, j) = value2;
			}
		}
		masks.Top = blendMask[0];
		masks.Bottom = blendMask[3];
		cv::Mat tmpImage;
		blendMask[0].convertTo(tmpImage, CV_8U, 255);
		cv::imwrite("result/blending/TopMask.png", tmpImage);
		blendMask[2].convertTo(tmpImage, CV_8U, 255);
		cv::imwrite("result/blending/Buttom.png", tmpImage);
		return masks;
	}

	BlendMask createBlendMask(int rows, int cols, Neighbor neighbor) {

		int maxInt = std::numeric_limits<int>::max();

		BlendMask masks;
		masks.Left = masks.Top = masks.Right = masks.Bottom = cv::Mat_<float>(
				rows, cols, 0.0);

		cv::Mat_<float> blendMask[4];
		blendMask[0] = cv::Mat_<float>(rows, cols, 0.0);
		blendMask[1] = cv::Mat_<float>(rows, cols, 0.0);
		blendMask[2] = cv::Mat_<float>(rows, cols, 0.0);
		blendMask[3] = cv::Mat_<float>(rows, cols, 0.0);

		float value[4] = { 0.0, 0.0, 0.0, 0.0 };
		cv::Mat_<float> totalMask;
		int d[4], dtemp[4], index[4];
		float cum = 0.0;
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				/*d[0]=dtemp[0]=neighbor.Left==ImageInfo::NONE?numeric_limits<int>::max():col;
				 d[1]=dtemp[1]=neighbor.Top==ImageInfo::NONE?numeric_limits<int>::max():row;
				 d[2]=dtemp[2]=neighbor.Right==ImageInfo::NONE?numeric_limits<int>::max():cols-col;
				 d[3]=dtemp[3]=neighbor.Bottom==ImageInfo::NONE?numeric_limits<int>::max():rows-row;*/
				int denominator[4];
				if (row == col && row == 0) {
					value[0] = 0.5;
					value[1] = 0.5;
					value[2] = 0.0;
					value[3] = 0.0;
				} else {
					int minX = cv::min(col, cols - col);
					int minY = cv::min(row, rows - row);
					float valueX = (1.0 - (float) minX / (minX + minY));
					float valueY = (1 - (float) minY / (minX + minY));
					value[0] = valueX * (1 - (float) col / cols);
					value[2] = valueX * (float) col / cols;
					value[1] = valueY * (1 - (float) row / rows);
					value[3] = valueY * (float) row / rows;
					cum = value[0] + value[1] + value[2] + value[3];
				}
				blendMask[0].at<float>(row, col) = value[0];
				blendMask[1].at<float>(row, col) = value[1];
				blendMask[2].at<float>(row, col) = value[2];
				blendMask[3].at<float>(row, col) = value[3];

				//log.Write(resultFile,"(%d,%d)value[0]=%f value[1]=%f value[2]=%f value[3]=%f , sum=%f\n",row,col,value[0],value[1],value[2],value[3],cum);
			}
		}

		masks.Left = blendMask[0];
		masks.Top = blendMask[1];
		masks.Right = blendMask[2];
		masks.Bottom = blendMask[3];

		return masks;
	}

	long getImageArea(cv::Mat image) {
		return image.rows * image.cols;
	}

	void stich(cv::Mat base, cv::Mat target,cv::Mat homography, cv::Mat& panorama){


	cv::Mat corners1(1, 4,CV_32F);
	cv::Mat corners2(1,4,CV_32F);
	cv::Mat corners(1,4,CV_32F);
	std::vector<cv::Mat> planes;
	/* compute corners
	of warped image
	*/
	corners1.at<float>(0,0)=0;
	corners2.at<float>(0,0)=0;
	corners1.at<float>(0,1)=0;
	corners2.at<float>(0,1)=target.rows;
	corners1.at<float>(0,2)=target.cols;
	corners2.at<float>(0,2)=0;
	corners1.at<float>(0,3)=target.cols;
	corners2.at<float>(0,3)=target.rows;

	planes.push_back(corners1);
	planes.push_back(corners2);

	merge(planes,corners);

	perspectiveTransform(corners, corners, homography);

	/* compute size of resulting
	image and allocate memory
	*/
	double x_start = cv::min( cv::min( (double)corners.at<cv::Vec2f>(0,0)[0], (double)corners.at<cv::Vec2f> (0,1)[0]),0.0);
	double x_end   = cv::max( cv::max( (double)corners.at<cv::Vec2f>(0,2)[0], (double)corners.at<cv::Vec2f>(0,3)[0]), (double)base.cols);
	double y_start = cv::min( cv::min( (double)corners.at<cv::Vec2f>(0,0)[1], (double)corners.at<cv::Vec2f>(0,2)[1]), 0.0);
	double y_end   = cv::max( cv::max( (double)corners.at<cv::Vec2f>(0,1)[1], (double)corners.at<cv::Vec2f>(0,3)[1]), (double)base.rows);

	/*Creating image
	with same channels, depth
	as target
	and proper size
	*/
	panorama.create(cv::Size(x_end - x_start + 1, y_end - y_start + 1), target.depth());

	planes.clear();

	/*Planes should
	have same n.channels
	as target
	*/
	for (int i=0;i<target.channels();i++){
	planes.push_back(panorama);
	}

	merge(planes,panorama);
	cv::Mat panorama1=panorama.clone();
	cv::Mat panorama2=panorama.clone();
	    // create translation matrix in order to copy both images to correct places
	cv::Mat T;
	T=cv::Mat::zeros(3,3,CV_64F);
	T.at<double>(0,0)=1;
	T.at<double>(1,1)=1;
	T.at<double>(2,2)=1;
	T.at<double>(0,2)=-x_start;
	T.at<double>(1,2)=-y_start;

	// copy base image to correct position within output image

	cv::warpPerspective(base, panorama1, T,panorama.size(),cv::INTER_LINEAR| CV_WARP_FILL_OUTLIERS);
	cv::imwrite("output/1.png",panorama1);
	// change homography to take necessary translation into account
	gemm(T, homography,1,T,0,T);
	    // warp second image and copy it to output image
	cv::warpPerspective(target,panorama2, T.inv(), panorama.size(),cv::INTER_LINEAR);
	cv::imwrite("output/2.png",panorama2);

	cv::addWeighted(panorama1,0.5,target,0.5,0.0,panorama);
	 //tidy
	corners.release();
	T.release();
	}

};

