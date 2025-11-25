Option Explicit
On Error Resume Next

' Declare our vars
Dim objWinHttp, strURL
Dim objWSH
Dim objFileSystem, objOutputFile
Dim strOutputFile
Dim urlStatus

Const OPEN_FILE_FOR_APPENDING = 8

strOutputFile = "c:\\eapp_vbs_log\\vbs_notification_log.log"


' Could also hard code if you want:
'strURL = "https://e-app-prd.housing.hksarg:9483/ea/Jsp/common/SendInitNotification.jsp"
strURL = "https://e-app-prd.housing.hksarg:9483/ea/Jsp/common/SendInitNotification.jsp"

Set objFileSystem = CreateObject("Scripting.fileSystemObject")

if objFileSystem.FileExists(strOutputFile) Then
	Set objOutputFile = objFileSystem.OpenTextFile(strOutputFile, OPEN_FILE_FOR_APPENDING)
else
	Set objOutputFile = objFileSystem.CreateTextFile(strOutputFile, TRUE)
end If

objOutputFile.WriteLine("[" & date & " " & time & "]" & "The Reminder Job started!")

Set objWinHttp = CreateObject("Microsoft.xmlhttp") '("WinHttp.WinHttpRequest.5")
objWinHttp.Open "GET", strURL, false
objWinHttp.Send

urlStatus = objWinHttp.Status

Set objWSH = WScript.CreateObject("Wscript.Shell")


If urlStatus <> 200 Then
	Err.Raise 60001, "e-Appraisal Scheduled Task", "The requested URL is not valid or no response. " & "urlStatus: " & urlStatus 
End If

If Err.Number <> 0 Then
	objWSH.Popup "Auto Notification fail.", 3, "e-Appraisal Scheduled Task", vbOKonly
	objOutputFile.WriteLine("[" & date & " " & time & "]" & "The Reminder Job failed!" & ", Reason : " & Err.Description)
Else
	objWSH.Popup "Auto Notification was sent successfully.", 3, "e-Appraisal Scheduled Task", vbOKonly
	objOutputFile.WriteLine("[" & date & " " & time & "]" & "The Reminder Job successfully ended!")
End If

Set objWinHttp = Nothing
Set objOutputFile = Nothing
Set objFileSystem = Nothing