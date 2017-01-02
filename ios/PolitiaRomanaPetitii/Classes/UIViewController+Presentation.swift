//
//  UIViewController+Presentation.swift
//  PolitiaRomanaPetitii
//
//  Created by Mihai Dumitrache on 02/01/2017.
//  Copyright © 2016 Work In Progress. All rights reserved.
//

import UIKit

extension UIViewController {
  
  func presentFromMainWindow(animated: Bool, completion: (() -> Void)?) {
    if let rootVC = UIApplication.shared.keyWindow?.rootViewController {
      presentFromController(controller: rootVC, animated: animated, completion: completion)
    }
  }
  
  private func presentFromController(controller: UIViewController, animated: Bool, completion: (() -> Void)?) {
    if let navVC = controller as? UINavigationController,
      let visibleVC = navVC.visibleViewController {
      presentFromController(controller: visibleVC, animated: animated, completion: completion)
    } else {
      if  let tabVC = controller as? UITabBarController,
        let selectedVC = tabVC.selectedViewController {
        presentFromController(controller: selectedVC, animated: animated, completion: completion)
      } else {
        controller.present(self, animated: animated, completion: completion)
      }
    }
  }
}
