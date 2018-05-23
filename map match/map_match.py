import pandas as pd
import json
import requests
from googlemaps import convert
import gmplot
df = pd.read_csv(r"df_gps.csv")
API_BASE_URL = "https://roads.googleapis.com/v1/snapToRoads"
long = df['gps_longitude']
lat = df['gps_latitude']
lat_long = []
new_lat = []
new_long = []
for i in range(len(long)):
    lat_long.append(str(lat[i])+','+str(long[i]))
for j in range(0, len(lat_long), 20):
    locations = convert.location_list(lat_long[j:j+20])
    params = {"path": locations, 'interpolate': "true", 'key': "AIzaSyBVtDB16c0FTGw1-L8iBuEdaQ937nwNXyI"}
    response = requests.get(API_BASE_URL, params=params)
    newgps = json.loads(response.text)
    for k in range(len(newgps['snappedPoints'])):
        new_lat.append(newgps['snappedPoints'][k]['location']['latitude'])
        new_long.append(newgps['snappedPoints'][k]['location']['longitude'])
dic = {'new_gps_longitude': new_long, 'new_gps_latitude': new_lat}
df2 = pd.DataFrame(dic)
df2.to_csv('new_gps.csv')
df = pd.read_csv(r"new_gps.csv")
long = df['new_gps_longitude']
lat = df['new_gps_latitude']
# df = pd.read_csv(r"df_gps.csv")
# long = df['gps_longitude']
# lat = df['gps_latitude']
gmap = gmplot.GoogleMapPlotter(lat[0], long[0], 16)
gmap.polygon(lat, long, '#FF6666', edge_width=10)
gmap.draw('mapmatch.html')