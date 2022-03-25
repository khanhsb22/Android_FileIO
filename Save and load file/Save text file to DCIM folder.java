 private void saveLicenseKeyFile(String fileName, String content) {
        File path;
        // Save file to Android/data/com.example.facerecognition/files/DCIM/face_id_sdk folder
        // For Android 11
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            path = getExternalFilesDir(Environment.DIRECTORY_DCIM + "/face_id_sdk");
        } else {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/face_id_sdk");
        }
        File file = new File(path, fileName + ".txt");
        if (!path.exists()) {
            path.mkdir();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] byteArray = content.getBytes();
            fos.write(byteArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "file not found " + e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "error " + e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "error " + e);
                }
            }
        }
    }

    private void readLicenseKeyFile(String fileName, EditText editText) {
        File path;
        // Get file from Android/data/com.example.facerecognition/files/DCIM/face_id_sdk folder
        // For Android 11
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            path = getExternalFilesDir(Environment.DIRECTORY_DCIM + "/face_id_sdk");
        } else {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/face_id_sdk");
        }
        File file = new File(path, fileName + ".txt");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text = "";
            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            editText.setText(sb.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "file not found " + e);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "error " + e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "error " + e);
                }
            }
        }
    }