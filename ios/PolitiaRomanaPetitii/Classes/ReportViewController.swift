//
//  ReportViewController.swift
//  PolitiaRomanaPetitii
//
//  Created by Mihai Dumitrache on 02/01/2017.
//  Copyright © 2016 Work In Progress. All rights reserved.
//

import UIKit

class ReportViewController: UIViewController {
  
  fileprivate var contentView: ReportView { get { return self.view as! ReportView } }
  fileprivate var imagePicker: ImagePicker?
  
  override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {
    super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
  }
  
  required init?(coder aDecoder: NSCoder) {
    super.init(coder: aDecoder)
  }
  
  override func loadView() {
    let view = ReportView()
    view.photoSelector.dataSource = self
    view.onLocateMe = {[weak self] in
      self?.locateMe()
    }
    view.onSend = {[weak self] in
      self?.sendPressed()
    }
    self.view = view
  }
  
  override func viewDidLoad() {
    super.viewDidLoad()
    
    self.title = NSLocalizedString("New report", comment: "")
    self.navigationController?.isNavigationBarHidden = false
    
    // Start tracker
    do {
      try LocationTracker.instance.startTracker()
    } catch {
      DLog(error)
    }
  }
  
  fileprivate func sendPressed() {
    self.view.endEditing(true)
    
    let images = self.contentView.images
    if images.count == 0 {
      AlertView.show(withMessage: NSLocalizedString("Attach at least one picture", comment: ""))
      return
    }
    
    guard let address = self.contentView.address, address.characters.count > 0 else {
      AlertView.show(withMessage: NSLocalizedString("Provide event location", comment: ""))
      return
    }
    let comments = self.contentView.comments ?? ""
    
    DLog("Sending \(comments)")
    
    /*
    ActivityIndicator.show()
    self.config.dataProvider.doReport(images: images, address: address, comment: comments, lat: 0.0, long: 0.0) {[weak self] (_, error) in
      ActivityIndicator.hide()
      
      if let error = error {
        AlertView.show(withMessage: error)
        return
      }
      
      // Show thank you message
        AlertView.show(withMessage: NSLocalizedString("Thank you! Your report has been sent.", comment: ""))
      
      // Reset UI
      self?.contentView.resetUI()
    }
     */
  }
  
  fileprivate func locateMe() {
    LocationTracker.instance.getLocationAddress {[weak self] (address) in
      self?.contentView.address = address
    }
  }
  
  fileprivate func showImagePicker(from controller: UIViewController, withCompletion completion: PhotoHandler) {
    self.imagePicker = ImagePicker(withCompletion: {[unowned self] (image) -> Void in
      completion?(image)
      self.imagePicker = nil
    })
    imagePicker?.showFromController(viewController: controller)
  }
}

extension ReportViewController: ReportPhotoSelectorDataSource {
  
  func pickImage(completion: PhotoHandler) {
    self.showImagePicker(from: self, withCompletion: completion)
  }
}
