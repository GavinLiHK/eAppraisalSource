oM=new makeCM("oM"); oM.resizeCheck=1; oM.rows=1;  oM.onlineRoot=""; oM.pxBetween =0; 
oM.fillImg="/jsp/images/cm_fill.gif"; oM.fromTop=32; oM.fromLeft=10; oM.wait=300; oM.zIndex=400;
oM.useBar=1; oM.barWidth="780"; oM.barHeight="menu"; oM.barX=0;oM.barY="menu"; oM.barClass="clBar";
oM.barBorderX=0; oM.barBorderY=0;
oM.menuPlacement=0;

oM.level[0]=new cm_makeLevel(90,21,"clT","clTover",1,1,"clB",0,"bottom",0,0,0,0,0);
oM.level[1]=new cm_makeLevel(102,22,"clS","clSover",1,1,"clB",0,"right",0,0,0,10,10);
oM.level[2]=new cm_makeLevel(110,22,"clS","clSover");
oM.level[3]=new cm_makeLevel(140,22); 

oM.makeMenu('m1','','Acquisition','');
oM.makeMenu('m11','m1','Supplier','../pgm/SupplierEnquiry.jsp?pageAction=reset','',160,0);
oM.makeMenu('m12','m1','Deal Processing','','',160,0);
oM.makeMenu('m121','m12','Deals and Deal Memos','../pgm/DealMaintenanceEnquiry.jsp?pageAction=reset','',200,0);
oM.makeMenu('m122','m12','All Titles','../pgm/DealTitleMaintenanceEnquiry.jsp?pageAction=reset','',200,0);
oM.makeMenu('m123','m12','Transfer Completed Deals','../pgm/DealTransferEnquiry.jsp?pageAction=reset','',200,0);
oM.makeMenu('m124','m12','Copy Completed Deals','../pgm/DealCopyEnquiry.jsp?pageAction=reset','',200,0);
oM.makeMenu('m13','m1','Report & Analyses','','',160,0);
oM.makeMenu('m14','m1','Payment Control','../pgm/DealPaymentEnquiry.jsp?pageAction=reset','',160,0);

oM.makeMenu('m2','','Scheduling','');
oM.makeMenu('m21','m2','Patterns','','',160,0);
oM.makeMenu('m211','m21','Schedule Patterns Maintenance','../sch/SchedulePatternEnquiry.jsp?pageAction=reset','',200,0);
oM.makeMenu('m212','m21','Rerun Patterns Maintenance','../sch/RerunPatternEnquiry.jsp?pageAction=reset','',200,0);

oM.makeMenu('m22','m2','Schedule','','',160,0);
oM.makeMenu('m221','m22','Schedule Maintenance','../sch/ScheduleMaintain.jsp','',200,0);
oM.makeMenu('m222','m22','Promo Schedule Generation','','',200,0);

oM.makeMenu('m23','m2','Import/Export','','',160,0);
oM.makeMenu('m231','m23','Export Transmission Log','','',200,0);
oM.makeMenu('m232','m23','Export SMS Interface','','',200,0);
oM.makeMenu('m233','m23','Import As-run Log','','',200,0);

oM.makeMenu('m24','m2','Reports','','',160,0);

oM.makeMenu('m3','','Programme','');

oM.makeMenu('m33','m3','Programme','','',160,0);
oM.makeMenu('m331','m33','Programme Maintenance','../pgm/ProgrammeMaintenanceEnquiry.jsp','',200,0);
oM.makeMenu('m332','m33','Programme Batch Copy','../pgm/ProgrammeBatchCopyEnquiry.jsp','',200,0);
oM.makeMenu('m333','m33','Repackage Programme','../pgm/ProgrammeRepackageMaintenanceEnquiry.jsp','',200,0);
oM.makeMenu('m334','m33','Order Followup','../pgm/OrderFollowupEnquiry.jsp','',200,0);
oM.makeMenu('m335','m33','Mark Music Cue Sheet','../pgm/MarkMusicCueSheet.jsp','',200,0);

oM.makeMenu('m34','m3','Interstitial','','',160,0);
oM.makeMenu('m341','m34','Interstitial Maintenance','../pgm/InterstitialMaintenanceEnquiry.jsp','',200,0);
oM.makeMenu('m342','m34','Interstitial Type Maintenance','../pgm/InterstitialTypeList.jsp','',200,0);
oM.makeMenu('m343','m34','Interstitial Batch Copy','../pgm/InterstitialBatchCopyEnquiry.jsp','',200,0);
oM.makeMenu('m344','m34','Promo Distribution','../pgm/PromoDistributionEnquiry.jsp','',200,0);

oM.makeMenu('m35','m3','Local Production','','',160,0);
oM.makeMenu('m351','m35','Local Production Maintenance','../pgm/LocalProdEnquiry.jsp','',200,0);
oM.makeMenu('m352','m35','Local Production Batch Change','../pgm/LocalProdBatchChange.jsp','',200,0);

oM.makeMenu('m36','m3','D&amp;S','','',160,0);
oM.makeMenu('m361','m36','D&S Contract Maintenance','../pgm/DSContractMaintenanceEnquiry.jsp','',200,0);
oM.makeMenu('m362','m36','D&S Media Loan List Maintenance','../pgm/DSLoanListEnquiry.jsp','',200,0);

oM.makeMenu('m4','','Library','');
oM.makeMenu('m41','m4','Media Logistics','','',180,0);
oM.makeMenu('m411','m41','New Blank Media Registration','../lib/CreateBlankMedia.jsp?pageAction=reset','',220,0);
oM.makeMenu('m412','m41','Media Ship In','../lib/ShipIn.jsp?pageAction=reset&editMode=N','',220,0);
oM.makeMenu('m413','m41','Media Wipe Off','../lib/WipeOffMedia.jsp?pageAction=reset','',220,0);
oM.makeMenu('m414','m41','Media Write Off','../lib/WriteOffMedia.jsp?pageAction=reset','',220,0);
oM.makeMenu('m415','m41','Create Shipping Invoice','../lib/ShipOutInfo.jsp?pageAction=reset&editMode=N','',220,0);
oM.makeMenu('m416','m41','Media Ship Out','../lib/ShipOutMedia.jsp?pageAction=reset','',220,0);
oM.makeMenu('m417','m41','Print Shipping Invoice','../lib/PrintShippingInvoice.jsp?pageAction=reset','',220,0);
oM.makeMenu('m419','m41','Media Enquiry','../lib/MediaEnquiry.jsp?pageAction=reset','',220,0);
oM.makeMenu('m418','m41','Shipping Info Maintenance','../lib/ShippingMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m4110','m41','Media Maintenance','../lib/MediaMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m4111','m41','Print Media Label','../lib/PrintMediaLabel.jsp','',220,0);
oM.makeMenu('m4112','m41','Batch Location Update','../lib/BatchLocationUpdate.jsp','',220,0);
oM.makeMenu('m4113','m41','Stock Taking Handling','../lib/UploadStockTakingFile.jsp','',220,0);
oM.makeMenu('m4114','m41','Programme Segment Maintenance','../lib/ProgSegmentMaintain.jsp?pageAction=reset&userMode=L','',220,0);
oM.makeMenu('m4115','m41','House Number Amendment','../lib/SearchProgVersion.jsp?pageAction=reset','',220,0);

oM.makeMenu('m42','m4','Media Circulation','','',180,0);
oM.makeMenu('m421','m42','Media Loan','../lib/LoanMedia.jsp?pageAction=reset','',220,0);
oM.makeMenu('m422','m42','Media Renewal','../lib/RenewMedia.jsp?pageAction=reset','',220,0);
oM.makeMenu('m423','m42','Media Return','../lib/ReturnMedia.jsp?pageAction=reset','',220,0);

oM.makeMenu('m43','m4','Picklist','','',180,0);
oM.makeMenu('m431','m43','Picklist Maintenance','../lib/PicklistMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m432','m43','Picklist Enquiry','../lib/PicklistEnquiry.jsp?pageAction=reset','',220,0);

oM.makeMenu('m44','m4','Patron Maintenance','','',180,0);
oM.makeMenu('m441','m44','Patron Maintenance','../lib/PatronMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m442','m44','Patron Group Maintenance','../lib/PatronGroupMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m443','m44','Patron Enquiry Maintenance','../lib/PatronEnquiry.jsp?pageAction=reset','',220,0);

oM.makeMenu('m45','m4','Report','','',180,0);
oM.makeMenu('m451','m45','Media Materials Usage Statistics Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT001R','_blank',220,0);
oM.makeMenu('m452','m45','Acquired Programme Statistics Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT002R','_blank',220,0);
oM.makeMenu('m453','m45','Music Cue Sheet Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT003R','_blank',220,0);
oM.makeMenu('m454','m45','Blank Media Materials (Raw & Recycle) Loan Statistics Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT004R','_blank',220,0);
oM.makeMenu('m455','m45','Expired Programme Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT005R','_blank',220,0);
oM.makeMenu('m456','m45','Acquired Programme Daily Receipt Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT006R','_blank',220,0);
oM.makeMenu('m457','m45','Acuiqred Programme Ship Out Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT007R','_blank',220,0);
oM.makeMenu('m458','m45','Media Materials Erased Statistics Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT008R','_blank',220,0);
oM.makeMenu('m459','m45','Overdue Reminder Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT009R','_blank',220,0);
oM.makeMenu('m4510','m45','Material Loan, Renew and Return Log','http://localhost/sree/asaps?op=Replet&name=lib/LRT010R','_blank',220,0);
oM.makeMenu('m4511','m45','Acquired Programme Media Transferred Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT011R','_blank',220,0);
oM.makeMenu('m4512','m45','Programme Materials Loan and Return Statistics Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT012R','_blank',220,0);
oM.makeMenu('m4513','m45','Media Materials Consumption and Usage Breakdown','http://localhost/sree/asaps?op=Replet&name=lib/LRT013R','_blank',220,0);
oM.makeMenu('m4514','m45','Media Inventory Breakdown','http://localhost/sree/asaps?op=Replet&name=lib/LRT014R','_blank',220,0);
oM.makeMenu('m4515','m45','On Loan Transmission Master Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT015R','_blank',220,0);
oM.makeMenu('m4516','m45','Transmission Media Log','http://localhost/sree/asaps?op=Replet&name=lib/LRT016R','_blank',220,0);
oM.makeMenu('m4517','m45','Data Checking Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT017R','_blank',220,0);
oM.makeMenu('m4518','m45','Media Materials Loan Statistics Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT018R','_blank',220,0);
oM.makeMenu('m4519','m45','Raw Media Materials Loan Statistics Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT019R','_blank',220,0);
oM.makeMenu('m4520','m45','Recycle Media Materials Loan Statistics Report','http://localhost/sree/asaps?op=Replet&name=lib/LRT020R','_blank',220,0);
oM.makeMenu('m4521','m45','Picklist','http://localhost/sree/asaps?op=Replet&name=lib/LRT021R','_blank',220,0);
oM.makeMenu('m4522','m45','Audit Information for Library Module','http://localhost/sree/asaps?op=Replet&name=lib/LRT022R','_blank',220,0);

oM.makeMenu('m5','','Finance','');
oM.makeMenu('m51','m5','Billing','','',180,0);
oM.makeMenu('m511','m51','Produce Auto Invoice','../fin/ProduceAutoInvoice.jsp?pageAction=reset','',220,0);
oM.makeMenu('m512','m51','Create Proforma Invoice','../fin/CreateProformaInvoice.jsp?pageAction=reset','',220,0);
oM.makeMenu('m514','m51','Invoice Maintenance','../fin/InvoiceMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m515','m51','Create Receipt','../fin/CreateReceipt.jsp?pageAction=reset','',220,0);
oM.makeMenu('m516','m51','Receipt Maintenance','../fin/ReceiptMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m517','m51','Invoice / Receipt Posting','../fin/InvoiceReceiptPosting.jsp?pageAction=reset','',220,0);
oM.makeMenu('m518','m51','Generate Reminder','../fin/GenerateReminder.jsp?pageAction=reset','',220,0);
oM.makeMenu('m519','m51','Invoice Enquiry','../fin/InvoiceEnquiry.jsp?pageAction=reset','',220,0);
oM.makeMenu('m5110','m51','Settlement Enquiry','../fin/SettlementEnquiry.jsp?pageAction=reset','',220,0);
/*oM.makeMenu('m5111','m51','AR Parameters Maintenance','../fin/ArParameterMaintain','',220,0);
*/
oM.makeMenu('m5112','m51','Export AR Interface','../fin/GenerateArInterface.jsp?pageAction=reset','',220,0);
oM.makeMenu('m5113','m51','Contract Remark Maintenance','../fin/ContractRemarkMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m5114','m51','Interface Cancellation','../fin/CancelInterface.jsp?pageAction=reset','',220,0);
oM.makeMenu('m5115','m51','Interface Status Enquiry','../fin/InterfaceStatusEnquiry.jsp?pageAction=reset','',220,0);
oM.makeMenu('m5116','m51','Interface Regeneration','../fin/RegenerateInterface.jsp?pageAction=reset','',220,0);


oM.makeMenu('m52','m5','Account Payable','','',180,0);
oM.makeMenu('m521','m52','Generate Payment Schedule','../fin/GeneratePaymentSchedule.jsp?pageAction=reset','',220,0);
oM.makeMenu('m522','m52','Payment Schedule Enquiry','../fin/PaymentScheduleEnquiry.jsp?pageAction=reset','',220,0);
oM.makeMenu('m523','m52','AP Interface Posting','../fin/PaymentInterfacePosting.jsp?pageAction=reset','',220,0);
oM.makeMenu('m524','m52','Export Payment Voucher','../fin/PaymentVoucherInterface.jsp?pageAction=reset','',220,0);
oM.makeMenu('m525','m52','Interface Cancellation','../fin/CancelInterface.jsp?pageAction=reset','',220,0);
oM.makeMenu('m526','m52','Interface Status Enquiry','../fin/InterfaceStatusEnquiry.jsp?pageAction=reset','',220,0);
oM.makeMenu('m527','m52','Interface Regeneration','../fin/RegenerateInterface.jsp?pageAction=reset','',220,0);
oM.makeMenu('m528','m52','Import Voucher Interface File','../fin/ImportVoucherInterface.jsp?pageAction=reset','',220,0);
oM.makeMenu('m529','m52','Import Printed Cheque Interface File','../fin/ImportChequeInterface.jsp?pageAction=re	set','',220,0);

oM.makeMenu('m53','m5','Withholding Tax','','',180,0);
oM.makeMenu('m531','m53','Withholding Tax Rate Maintenance','../fin/WhTaxRateMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m532','m53','Deal Withholding Tax Maintenance','../fin/DealWhTaxMaintain.jsp?pageAction=reset','',220,0);

oM.makeMenu('m54','m5','General Ledger','','',180,0);
oM.makeMenu('m541','m54','Generate Capitalization Log','../fin/GenerateCapLog.jsp?pageAction=reset','',220,0);
oM.makeMenu('m542','m54','Generate Amortization Log','../fin/GenerateAmorLog.jsp?pageAction=reset','',220,0);
oM.makeMenu('m543','m54','Obsolescence Write Off Maintenance','../fin/WriteOffObso.jsp?pageAction=reset','',220,0);
oM.makeMenu('m544','m54','Generate Obsolescence Log','../fin/GenerateObsoLog.jsp?pageAction=reset','',220,0);
oM.makeMenu('m545','m54','Capitalization Posting','../fin/CapPosting.jsp?pageAction=reset','',220,0);
oM.makeMenu('m546','m54','Amortization Posting','../fin/AmorPosting.jsp?pageAction=reset','',220,0);
oM.makeMenu('m547','m54','Obsolescence Posting','../fin/ObsoPosting.jsp?pageAction=reset','',220,0);
oM.makeMenu('m548','m54','Export Capitalization Interface','../fin/GenerateCapInterface.jsp?pageAction=reset','',220,0);
oM.makeMenu('m549','m54','Export Amortization Interface','../fin/GenerateAmorInterface.jsp?pageAction=reset','',220,0);
oM.makeMenu('m5410','m54','Export Obsolescence Inteface','../fin/GenerateObsoInterface.jsp?pageAction=reset','',220,0);
oM.makeMenu('m5411','m54','Interface Cancellation','../fin/CancelInterface.jsp?pageAction=reset','',220,0);
oM.makeMenu('m5412','m54','Interface Status Enquiry','../fin/InterfaceStatusEnquiry.jsp?pageAction=reset','',220,0);
oM.makeMenu('m5413','m54','Interface Regeneration','../fin/RegenerateInterface.jsp?pageAction=reset','',220,0);


oM.makeMenu('m55','m5','Budget Control','','',180,0);
oM.makeMenu('m551','m55','Budget Category Maintenance','../fin/BudgetCategoryMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m552','m55','Budget Import','../fin/ImportBudget.jsp?pageAction=reset','',220,0);
oM.makeMenu('m553','m55','PPR Date Maintenance','../fin/PprDateMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m554','m55','PPR Budget Allocation','../fin/BudgetAllocation.jsp?pageAction=reset','',220,0);
oM.makeMenu('m555','m55','Cancel PPR Budget Allocation','../fin/CancelBudgetAllocation.jsp?pageAction=reset','',220,0);
oM.makeMenu('m556','m55','Budget Amount Enquiry','../fin/BudgetAmountEnquiry.jsp?pageAction=reset','',220,0);

oM.makeMenu('m56','m5','Revenue Sharing','','',180,0);
oM.makeMenu('m561','m56','Interface to PPVRS','../fin/GeneratePpvrsInterface.jsp?pageAction=reset','',220,0);

oM.makeMenu('m57','m5','System Setup','','',180,0);
oM.makeMenu('m571','m57','Cost Center Maintenance','../fin/CostCenterMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m572','m57','Cost Center Structure Maintenance','../fin/CostCenterStructureMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m573','m57','Account Maintenance','../fin/AccountMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m574','m57','Account Mapping Maintenance','../fin/AccountMappingMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m575','m57','Account Code Maintenance','../fin/AccountCodeMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m576','m57','Corp Code Maintenance','../fin/CorpCodeMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m577','m57','Sub-ledger ID Maintenance','../fin/SubledgerMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m578','m57','Vendor Code Maintenance','../fin/VendorMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m579','m57','Amortization Type Maintenance','../fin/AmorTypeMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m5710','m57','Currency Maintenance','../fin/CurrencyMaintain.jsp?pageAction=reset','',220,0);
oM.makeMenu('m5711','m57','Exchange Rate Maintenance','../fin/ExchangeRateMaintain.jsp?pageAction=reset','',220,0);

oM.makeMenu('m58','m5','Reports','','',180,0);
oM.makeMenu('m581','m58','GL reports','','',220,0);
oM.makeMenu('m5811','m581','Reminder on program that will soon be expired and not fully amortized (FRP004R)','../fin/GLParameterSheet.jsp?reportPath=fin/FRP004R&parameterType=A','',220,0);
oM.makeMenu('m5812','m581','Authorization for Provision for Purchased Program Right Obsolescence(FRP005R)','../fin/GLParameterSheet.jsp?reportPath=fin/FRP005R&parameterType=A','',220,0);
oM.makeMenu('m5813','m581','PPV Movies Library Detail(FRP010R_1)','../fin/GLParameterSheet.jsp?reportPath=fin/FRP010R_1&parameterType=D','',220,0);
oM.makeMenu('m5814','m581','Summary of PPV Movies Library ¡V by Year of Expiry(FRP010R_2)','../fin/GLParameterSheet.jsp?reportPath=fin/FRP010R_2&parameterType=C','',220,0);
oM.makeMenu('m5815','m581','Summary of PPV Movies Library ¡V Current Month Movement(FRP010R_3)','../fin/GLParameterSheet.jsp?reportPath=fin/FRP010R_3&parameterType=C','',220,0);

oM.makeMenu('m582','m58','AP reports','','',220,0);


oM.makeMenu('m6','','Ad-Sales','');

oM.makeMenu('m7','','Administration','');
oM.makeMenu('m71','m7','User Maintenance','','',180,0);
oM.makeMenu('m771','m71','Create User','','',220,0);

oM.construct()
