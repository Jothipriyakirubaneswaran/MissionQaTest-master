@echo off
REM Run project tests (requires Maven on PATH)
where mvn >nul 2>&1
if %ERRORLEVEL% neq 0 (
  echo Maven (mvn) not found on PATH.
  echo Please install Maven or run the tests from your IDE after building the project.
  echo Download Maven: https://maven.apache.org/download.cgi
  echo After installing, run: mvn test
  exit /b 1
)
echo Running mvn test...
mvn test
exit /b %ERRORLEVEL%