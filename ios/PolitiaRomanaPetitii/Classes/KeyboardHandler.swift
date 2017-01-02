//
//  KeyboardHandler.swift
//  PolitiaRomanaPetitii
//
//  Created by Mihai Dumitrache on 02/01/2017.
//  Copyright © 2016 Work In Progress. All rights reserved.
//

import Foundation
import UIKit

class KeyboardHandler {
  
  fileprivate var keyboardHeight : CGFloat = 0
  fileprivate var animationDuration : Float = 0.0
  fileprivate var animationCurve : Int = 0
  
  var handler: ((_ keyboardHeight: CGFloat, _ curve: NSInteger, _ duration: CGFloat) -> Void)?
  
  init() {
    self.addNotifications()
  }
  
  deinit {
    NotificationCenter.default.removeObserver(self)
  }
  
  func addNotifications() {
    let notification = NotificationCenter.default
    notification.addObserver(self, selector: #selector(KeyboardHandler.keyboardWillShow(note:)), name: NSNotification.Name.UIKeyboardWillShow, object: nil)
    notification.addObserver(self, selector: #selector(KeyboardHandler.keyboardWillHide(note:)), name: NSNotification.Name.UIKeyboardWillHide, object: nil)
    notification.addObserver(self, selector: #selector(KeyboardHandler.keyboardWillChangeFrame(note:)), name: NSNotification.Name.UIKeyboardWillChangeFrame, object: nil)
  }
  
  @objc func keyboardWillShow(note: NSNotification) {
    if let info = note.userInfo as NSDictionary? {
      self.animationCurve = (info[UIKeyboardIsLocalUserInfoKey] as! NSNumber).intValue
      self.animationDuration = (info[UIKeyboardAnimationDurationUserInfoKey] as! NSNumber).floatValue
      
      self.callHandler()
    }
  }
  
  @objc func keyboardWillHide(note: NSNotification) {
    if let info = note.userInfo as NSDictionary? {
      self.animationCurve = (info[UIKeyboardIsLocalUserInfoKey] as! NSNumber).intValue
      self.animationDuration = (info[UIKeyboardAnimationDurationUserInfoKey] as! NSNumber).floatValue
      
      self.keyboardHeight = 0
      self.callHandler()
    }
  }
  
  @objc func keyboardWillChangeFrame(note: NSNotification) {
    if let info = note.userInfo as NSDictionary? {
      let rect = (info[UIKeyboardFrameEndUserInfoKey] as! NSValue).cgRectValue
      self.keyboardHeight = rect.height
    }
  }
  
  func callHandler() {
    handler?(self.keyboardHeight, self.animationCurve, CGFloat(self.animationDuration))
  }
}
