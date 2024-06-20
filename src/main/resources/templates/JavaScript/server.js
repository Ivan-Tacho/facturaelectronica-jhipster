import express from "express";
import cors from "cors";
import multer from "multer";

const app = express();

//Cross-Origin Resource Sharing (CORS) is an HTTP-header based mechanism that allows a server to indicate any origins
app.use(cors());

const storage = multer.diskStorage({
  destination: (req, file, cb) => {
    cb(null, __dirname + "/uploads");
  },
  filename: (req, file, cb) => {
    cb(null, file.originalname);
  },
});

const Data = multer({ storage: storage });

app.post("/files", Data.any("files"), (req, res) => {
  if (res.status(200)) {
    console.log("Your file has been uploaded successfully.");
    console.log(req.files);
    res.json({ message: "Successfully uploaded files" });
    res.end();
  }
});

app.listen(8000, () => {
  console.log("Server is running");
});