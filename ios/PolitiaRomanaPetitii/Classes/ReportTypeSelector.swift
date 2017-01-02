//
//  ReportTypeSelector.swift
//  PolitiaRomanaPetitii
//
//  Created by Mihai Dumitrache on 02/01/2017.
//  Copyright © 2017 GovITHub. All rights reserved.
//

import UIKit

enum ReportType: Int {
  case type1
  case type2
  
  var name: String {
    switch self {
    case .type1: return "Type 1"
    case .type2: return "Type 2"
    }
  }
  
  static var count: Int { return ReportType.type2.rawValue + 1}
}

class ReportTypeSelector: UIPickerView {
  
  var onSelect: ((_ type: ReportType) ->  Void)?
  
  init() {
    super.init(frame: CGRect.zero)
    self.setup()
  }
  
  required init?(coder aDecoder: NSCoder) {
    fatalError("init(coder:) has not been implemented")
  }
  
  fileprivate func setup() {
    self.dataSource = self
    self.delegate = self
  }
}

extension ReportTypeSelector: UIPickerViewDataSource, UIPickerViewDelegate {
 
  func numberOfComponents(in pickerView: UIPickerView) -> Int {
    return 1
  }
  
  func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
    return ReportType.count
  }
  
  func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
    let type = ReportType(rawValue: row)
    return type!.name
  }
  
  func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
    onSelect?(ReportType(rawValue: row)!)
  }
}
