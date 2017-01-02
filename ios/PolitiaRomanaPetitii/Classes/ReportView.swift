//
//  ReportView.swift
//  PolitiaRomanaPetitii
//
//  Created by Mihai Dumitrache on 02/01/2017.
//  Copyright © 2016 Work In Progress. All rights reserved.
//

import UIKit
import Cartography

class ReportView: UIView {
  
  fileprivate let keyboardHandler = KeyboardHandler()
  
  fileprivate var scrollView: UIScrollView = {
    let scrollView = UIScrollView()
    scrollView.backgroundColor = UIColor.white
    scrollView.contentInset = UIEdgeInsetsMake(64, 0, 0, 0)
    scrollView.scrollIndicatorInsets = UIEdgeInsetsMake(64, 0, 0, 0)
    return scrollView
  }()
  
  fileprivate var scrollWidthView: UIView = {
    let view = UIView()
    view.isHidden = true
    return view
  }()
  
  fileprivate var scrollViewContentWidthConstraint : NSLayoutConstraint!
  
  fileprivate lazy var reportTypeSelector: ReportTypeSelector = {
    let typeSelector = ReportTypeSelector()
    typeSelector.onSelect = {[unowned self] type in
      self.reportType = type
      self.reportTypeTextField.text = type.name
      self.reportTypeTextField.resignFirstResponder()
    }
    return typeSelector
  }()
  
  fileprivate lazy var reportTypeTextField: UITextField = {
    let label = UITextField()
    label.font = UIFont.fontTitleText()
    label.textColor = UIColor.textColor()
    label.placeholder = NSLocalizedString("Report type", comment: "")
    label.backgroundColor = UIColor.white
    label.inputView = self.reportTypeSelector
    return label
  }()
  
  fileprivate var photoTitleLabel: UILabel = {
    let label = UILabel()
    label.font = UIFont.fontTitleText()
    label.text = NSLocalizedString("Pictures from location", comment: "")
    label.textColor = UIColor.textColor()
    return label
  }()
  
  var photoSelector: ReportPhotoSelector = {
    let view = ReportPhotoSelector()
    return view
  }()
  
  fileprivate var locationButton: UIButton = {
    let button = UIButton(type: .custom)
    button.setImage(UIImage(named: "getLocation"), for: .normal)
    return button
  }()
  
  fileprivate var addressTextField: UITextField = {
    let label = UITextField()
    label.font = UIFont.fontTitleText()
    label.textColor = UIColor.textColor()
    label.placeholder = NSLocalizedString("Address", comment: "")
    label.backgroundColor = UIColor.white
    return label
  }()
  
  fileprivate var commentsView: ReportInputView = {
    let view = ReportInputView()
    return view
  }()
  
  fileprivate var sendView: RoundedButton = {
    let title = NSLocalizedString("Send report", comment: "")
    let view = RoundedButton(title: title)
    return view
  }()
  
  var images: [UIImage] { get { return self.photoSelector.images } }
  var address: String? {
    get {
      return self.addressTextField.text
    }
    set {
      self.addressTextField.text = newValue
    }
  }
  var comments: String? { get { return self.commentsView.text } }
  var reportType: ReportType?
  
  var onSend: (() -> Void)?
  var onLocateMe: (() -> Void)?
  
  init() {
    super.init(frame: CGRect.zero)
    self.setup()
    
    // Register handler for keyboard shown handler
    self.keyboardHandler.handler = {[weak self] height, curve, duration in
      self?.updateViewContentForKeyboard(height: height, curve: curve, duration: duration)
    }
  }
  
  required init?(coder aDecoder: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }
  
  fileprivate func setup() {
    
    // Scroll view
    self.addSubview(scrollView)
    constrain(scrollView) { view in
      view.edges == view.superview!.edges
    }
    
    // Add invisible view for content width
    scrollView.addSubview(scrollWidthView)
    constrain(scrollWidthView) { view in
      view.top == view.superview!.top
      view.leading == view.superview!.leading
      view.trailing == view.superview!.trailing
      self.scrollViewContentWidthConstraint = view.width == 320
    }
    
    // Add photo label
    scrollView.addSubview(reportTypeTextField)
    constrain(reportTypeTextField) { view in
      view.top == view.superview!.top + 10
      view.leading == view.superview!.leading + 20
      view.trailing == view.superview!.trailing - 20
      view.height == 34
    }
    
    // Add separator
    let separator1 = LineView()
    scrollView.addSubview(separator1)
    constrain(separator1, reportTypeTextField) { view, topView in
      view.top == topView.bottom + 10
      view.leading == view.superview!.leading
      view.trailing == view.superview!.trailing
    }
    
    // Add photo label
    scrollView.addSubview(photoTitleLabel)
    constrain(photoTitleLabel, separator1) { view, topView in
      view.top == topView.bottom + 10
      view.leading == view.superview!.leading + 20
      view.trailing == view.superview!.trailing - 20
    }
    
    // Add photo selector view
    scrollView.addSubview(photoSelector)
    constrain(photoSelector, photoTitleLabel) { view, topView in
      view.top == topView.bottom + 8
      view.leading == view.superview!.leading
      view.trailing == view.superview!.trailing
    }
    
    // Add separator
    let separator2 = LineView()
    scrollView.addSubview(separator2)
    constrain(separator2, photoSelector) { view, topView in
      view.top == topView.bottom + 20
      view.leading == view.superview!.leading
      view.trailing == view.superview!.trailing
    }
    
    // Add address textfield
    locationButton.addTarget(self, action: #selector(locatePressed), for: .touchUpInside)
    scrollView.addSubview(locationButton)
    scrollView.addSubview(addressTextField)
    constrain(addressTextField, locationButton, separator2) { view, button, topView in
      button.width == 30
      button.height == 30
      button.trailing == view.superview!.trailing - 20
      button.centerY == view.centerY
      
      view.top == topView.bottom + 10
      view.leading == view.superview!.leading + 20
      view.trailing == button.leading - 20
      view.height == 34
    }
    
    // Add separator
    let separator3 = LineView()
    scrollView.addSubview(separator3)
    constrain(separator3, addressTextField) { view, topView in
      view.top == topView.bottom + 10
      view.leading == view.superview!.leading
      view.trailing == view.superview!.trailing
    }
    
    // Add comments view
    scrollView.addSubview(commentsView)
    constrain(commentsView, separator3) { view, topView in
      view.top == topView.bottom + 10
      view.leading == view.superview!.leading
      view.trailing == view.superview!.trailing
    }
    
    // Add send button
    sendView.addTarget(self, action: #selector(sendPressed), for: .touchUpInside)
    scrollView.addSubview(sendView)
    constrain(sendView, commentsView) { view, topView in
      view.top == topView.bottom + 20
      view.centerX == view.superview!.centerX
      view.bottom == view.superview!.bottom - 20
    }
  }
  
  func resetUI() {
    self.addressTextField.text = ""
    self.commentsView.text = ""
    self.photoSelector.resetUI()
  }
  
  override func layoutSubviews() {
    super.layoutSubviews()
    self.scrollViewContentWidthConstraint.constant = self.bounds.width
  }
  
  private func updateViewContentForKeyboard(height: CGFloat, curve: NSInteger, duration: CGFloat) {
    var insets = self.scrollView.contentInset
    insets.bottom = height
    self.scrollView.contentInset = insets
    self.scrollView.scrollIndicatorInsets = insets
  }
  
  @objc private func sendPressed() {
    onSend?()
  }
  
  @objc private func locatePressed() {
    onLocateMe?()
  }
}
