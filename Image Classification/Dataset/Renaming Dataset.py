import os
import shutil
import pathlib

ori_path = pathlib.Path("C:\\Users\\LENOVO\\Documents\\DATASET")

def renaming():
    count = 1
    for folder in ori_path.iterdir():
        if folder.is_dir():
            for image in folder.iterdir():
                if image.is_file():
                    newname= "image{}.jpg".format(count)
                    os.rename(image, newname)
                    count += 1

if __name__=='__main__':
    renaming()