CustomView 基础知识

坐标系原点为View左上角的那个点  x轴从左往右为正(递增) y轴从上往下为正(递增) y轴与数学坐标系相反

画一个圆 `canvas.drawCircle(circleX, circleY, radius, paint);`

画一个矩形 `canvas.drawRect(new RectF(secondStartX, oneStartY, rectXEnd, rectYEnd), paint);`
画一个矩形 `canvas.drawRect(secondStartX, oneStartY, rectXEnd, rectYEnd, paint);`

画一个椭圆 `canvas.drawOval(thirdStartX, oneStartY, ovalXEnd, ovalYEnd, paint);`

画一个点 `canvas.drawPoint(pointX, pointY, paint);`