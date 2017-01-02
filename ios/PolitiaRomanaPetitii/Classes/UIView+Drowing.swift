//
//  UIView+Drowing.swift
//  PolitiaRomanaPetitii
//
//  Created by Mihai Dumitrache on 02/01/2017.
//  Copyright © 2016 Work In Progress. All rights reserved.
//

import UIKit

extension UIView {
  
  class func draw1PxLine(context: CGContext, startPoint: CGPoint, endPoint: CGPoint, color: UIColor) {
    
    let scale = UIScreen.main.scale
    let width = 1 / scale
    let centerChoice: CGFloat = scale.truncatingRemainder(dividingBy: 2) == 0 ? 4 : 2
    let offset = scale / centerChoice * width
    
    context.setLineWidth(width)
    context.setStrokeColor(color.cgColor)
    
    let x1: CGFloat = startPoint.x + offset
    let y1: CGFloat = startPoint.y + offset
    let x2: CGFloat = endPoint.x + offset
    let y2: CGFloat = endPoint.y + offset
    
    context.beginPath()
    context.move(to: CGPoint(x: x1, y: y1))
    context.addLine(to: CGPoint(x: x2, y: y2))
    context.strokePath()
  }
}
