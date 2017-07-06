# testMyGoogleDisk
BASE:
main class: Application. It need run for start (Spring Boot)
controllers: FileController - have 3 request: 

@PostMapping("/upload")
  public Key upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "description", required = false) String description)
  
  Example: http://localhost:8080/upload with form-data: key=file, value=Choose any file
  description for this file can be null
  can to use Postman app (POST request)

@GetMapping("/get/{key}")
  public ResponseEntity Resource getFile(@PathVariable String key)
	
  Example: http://localhost:8080/get/keyExample, where keyExample get from POST request (upload) 
  can to use browser
  
@DeleteMapping("/delete/{key}")
  public FileInfo deleteFileByKey(@PathVariable String key)
  
  Example: http://localhost:8080/delete/keyExample, where keyExample get from POST request (upload) 
  can to use Postman app (DELETE request)

domain: in this pakcage store Entity(Table)
repository: store class with custom query
rest: objects for REST requests
storage: base confige for store data

YOU NEED! MySQL with database 'mygoogledisk' username 'root' password 'admin1234'. You can to change this properties in application.yml
BASE STORE: in root project will create directory 'upload-directory', when you first start application.
