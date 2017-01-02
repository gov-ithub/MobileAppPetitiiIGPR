//
//  UIFont+App.swift
//  PolitiaRomanaPetitii
//
//  Created by Mihai Dumitrache on 02/01/2017.
//  Copyright © 2016 Work In Progress. All rights reserved.
//

import UIKit

extension UIFont {
  
  class func fontAppRegular(_ size: CGFloat) -> UIFont {
    return UIFont.systemFont(ofSize: size)
  }
  
  class func fontAppBold(_ size: CGFloat) -> UIFont {
    return UIFont.boldSystemFont(ofSize: size)
  }
  
  class func fontAppMedium(_ size: CGFloat) -> UIFont {
    return UIFont(name: ".SFUIText-Medium", size: size)!
  }
  
  class func fontAppLight(_ size: CGFloat) -> UIFont {
    return UIFont(name: ".SFUIText-Light", size: size)!
  }
  
  class func fontTitleText() -> UIFont {
    return UIFont.fontAppLight(16)
  }
  
  class func fontRegularText() -> UIFont {
    return UIFont.systemFont(ofSize: 14)
  }
}
