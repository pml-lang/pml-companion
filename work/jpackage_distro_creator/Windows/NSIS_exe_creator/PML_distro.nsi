; Parameters
; !define APPLICATION_ID   "PML_to_HTML_Converter"
!define APPLICATION_NAME "PML to HTML Converter"
!define APPLICATION_VERSION "1.2.0"
!define DISTRIBUTION_DIRECTORY "C:\aa\work\PML\Converter\jpackage_distro\Windows"

; !define APPLICATION_CREATOR "Christian Neumanns"

; Computed Parameters
!define APPLICATION_FILE_NAME "${APPLICATION_NAME}-${APPLICATION_VERSION}"
 
; NSIS config
Unicode True
; Name "${APPLICATION_NAME}, version ${APPLICATION_VERSION}"
Name "${APPLICATION_NAME}"
OutFile "${DISTRIBUTION_DIRECTORY}/${APPLICATION_FILE_NAME}.exe"
InstallDir "$PROGRAMFILES64\${APPLICATION_NAME}"
; ShowInstDetails show

; Use modern UI (better look)
; https://nsis.sourceforge.io/Docs/Modern%20UI/Readme.html
; https://nsis.sourceforge.io/Examples/Modern%20UI/Basic.nsi
!include "MUI2.nsh"
; !define MUI_ABORTWARNING

;Pages
!insertmacro MUI_PAGE_WELCOME
; !insertmacro MUI_PAGE_LICENSE "${NSISDIR}\Docs\Modern UI\License.txt"
; !insertmacro MUI_PAGE_COMPONENTS
; !insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_INSTFILES
; !insertmacro MUI_PAGE_FINISH
; !insertmacro MUI_UNPAGE_CONFIRM
; !insertmacro MUI_UNPAGE_INSTFILES
!insertmacro MUI_LANGUAGE "English"


; install msi
Section "${APPLICATION_NAME}"

  DetailPrint "Installing '${APPLICATION_NAME}' to '$INSTDIR'"
  
  SetOutPath "$TEMP"
  File "${DISTRIBUTION_DIRECTORY}\${APPLICATION_FILE_NAME}.msi"
  
  ClearErrors
  ; ExecWait 'msiexec /i "$TEMP\${APPLICATION_FILE_NAME}.msi"'
  ; ExecWait 'msiexec /i "$TEMP\${APPLICATION_FILE_NAME}.msi" /quiet'
  ExecWait 'msiexec /i "$TEMP\${APPLICATION_FILE_NAME}.msi" /passive'

  IfErrors 0 +4
  DetailPrint "An unexpected error occured."
  MessageBox MB_ICONSTOP "An unexpected error occured.$\r$\nThe application could not be installed."
  Abort
SectionEnd

Section "Update Path"

  DetailPrint "Appending $INSTDIR to System Path"

  ; EnVar plugin must be installed https://nsis.sourceforge.io/EnVar_plug-in
  ; Use HKEY_LOCAL_MACHINE (for system variables)
  EnVar::SetHKLM
  EnVar::AddValueEx "Path" "$INSTDIR"
SectionEnd

Section "Restart"

  DetailPrint "Restart"

  MessageBox MB_YESNO|MB_ICONQUESTION "Before using the application your computer must be restarted.$\r$\nDo you want to restart now?" IDNO +2
  Reboot
SectionEnd
