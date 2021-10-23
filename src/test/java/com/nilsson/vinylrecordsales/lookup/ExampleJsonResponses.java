package com.nilsson.vinylrecordsales.lookup;

public class ExampleJsonResponses {
    public ExampleJsonResponses() {
    }

    public static String record() {
        return """
                   {
                    "country": "Sweden",
                    "year": "1986",
                    "format": ["Vinyl", "LP", "Album"],
                    "label": ["Mariann", "Mariann Records", "Mariann Records", "Fri Reklam", "KMH Studios", "SIB-Tryck AB, Norsborg", "Grammoplast"],
                    "type": "release",
                    "genre": ["Electronic", "Pop"],
                    "style": ["Synth-pop", "Schlager"],
                    "id": 2229646,
                    "barcode": ["BIEM/n\\u00a9b", "MLPH-1622-A 860414GP BA-GP", "MLPH-1622-B 860414GP BA-GP"],
                    "user_data": {
                     "in_wantlist": false,
                     "in_collection": false
                    },
                    "master_id": 315620,
                    "master_url": "https://api.discogs.com/masters/315620",
                    "uri": "/Lena-Philipsson-K%C3%A4rleken-%C3%84r-Evig/release/2229646",
                    "catno": "MLPH 1622",
                    "title": "Lena Philipsson - K\\u00e4rleken \\u00c4r Evig.",
                    "thumb": "https://img.discogs.com/K-iaOq7zlRC0q5RfRdA9U8VisCE=/fit-in/150x150/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/R-2229646-1320883037.jpeg.jpg",
                    "cover_image": "https://img.discogs.com/58RI-XKBhD42w1SKIJhXvUIMFqA=/fit-in/450x450/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-2229646-1320883037.jpeg.jpg",
                    "resource_url": "https://api.discogs.com/releases/2229646",
                    "community": {
                     "want": 20,
                     "have": 198
                    },
                    "format_quantity": 1,
                    "formats": [{
                     "name": "Vinyl",
                     "qty": "1",
                     "descriptions": ["LP", "Album"]
                    }]
                   }
                """;
    }

    public static String releaseInfo() {
        return """
                {
                  "id": 2229646,
                  "status": "Accepted",
                  "year": 1986,
                  "resource_url": "https://api.discogs.com/releases/2229646",
                  "uri": "https://www.discogs.com/release/2229646-Lena-Philipsson-K%C3%A4rleken-%C3%84r-Evig",
                  "artists": [
                    {
                      "name": "Lena Philipsson",
                      "anv": "",
                      "join": "",
                      "role": "",
                      "tracks": "",
                      "id": 158128,
                      "resource_url": "https://api.discogs.com/artists/158128"
                    }
                  ],
                  "artists_sort": "Lena Philipsson",
                  "labels": [
                    {
                      "name": "Mariann",
                      "catno": "MLPH 1622",
                      "entity_type": "1",
                      "entity_type_name": "Label",
                      "id": 140504,
                      "resource_url": "https://api.discogs.com/labels/140504"
                    }
                  ],
                  "series": [],
                  "companies": [
                    {
                      "name": "Mariann Records",
                      "catno": "",
                      "entity_type": "13",
                      "entity_type_name": "Phonographic Copyright (p)",
                      "id": 82474,
                      "resource_url": "https://api.discogs.com/labels/82474"
                    },
                    {
                      "name": "Mariann Records",
                      "catno": "",
                      "entity_type": "14",
                      "entity_type_name": "Copyright (c)",
                      "id": 82474,
                      "resource_url": "https://api.discogs.com/labels/82474"
                    }
                  ],
                  "formats": [
                    {
                      "name": "Vinyl",
                      "qty": "1",
                      "descriptions": [
                        "LP",
                        "Album"
                      ]
                    }
                  ],
                  "data_quality": "Needs Vote",
                  "community": {
                    "have": 210,
                    "want": 22,
                    "rating": {
                      "count": 17,
                      "average": 3.53
                    },
                    "submitter": {
                      "username": "Mr-Love",
                      "resource_url": "https://api.discogs.com/users/Mr-Love"
                    },
                    "contributors": [
                      {
                        "username": "Mr-Love",
                        "resource_url": "https://api.discogs.com/users/Mr-Love"
                      },
                      {
                        "username": "perja72",
                        "resource_url": "https://api.discogs.com/users/perja72"
                      }
                    ],
                    "data_quality": "Needs Vote",
                    "status": "Accepted"
                  },
                  "format_quantity": 1,
                  "date_added": "2010-04-12T19:26:18-07:00",
                  "date_changed": "2020-08-31T13:41:12-07:00",
                  "num_for_sale": 17,
                  "lowest_price": 1.98,
                  "master_id": 315620,
                  "master_url": "https://api.discogs.com/masters/315620",
                  "title": "Kärleken Är Evig.",
                  "country": "Sweden",
                  "released": "1986",
                  "notes": "Recorded and mixed spring 1986 in KMH Studios, Stockholm\\nPhotography by Håkan Andersson, Skara Fotostudio\\n\\nNote: Some copies come with a printed Mariann company inner sleeve, promoting other artists and Skara Sommarland.  ",
                  "released_formatted": "1986",
                  "identifiers": [
                    {
                      "type": "Rights Society",
                      "value": "BIEM/n©b"
                    },
                    {
                      "type": "Matrix / Runout",
                      "value": "MLPH-1622-A 860414GP BA-GP",
                      "description": "Side A, etched"
                    }
                  ],
                  "videos": [
                    {
                      "uri": "https://www.youtube.com/watch?v=Ajn_PPss6Qo",
                      "title": "Lena Philipsson - Kärleken är evig   Live MF",
                      "description": "Lena Philipsson - Kärleken är evig   Live MF",
                      "duration": 195,
                      "embed": true
                    },
                    {
                      "uri": "https://www.youtube.com/watch?v=4Gi9NA0YDg8",
                      "title": "JAG KÄNNER  LENA PHILIPSSON 1986",
                      "description": "från vhs band 1986",
                      "duration": 231,
                      "embed": true
                    }
                  ],
                  "genres": [
                    "Electronic",
                    "Pop"
                  ],
                  "styles": [
                    "Synth-pop",
                    "Schlager"
                  ],
                  "tracklist": [
                    {
                      "position": "A1",
                      "type_": "track",
                      "title": "Kärleken Är Evig",
                      "extraartists": [
                        {
                          "name": "T. Söderberg - P. Gessle",
                          "anv": "",
                          "join": "",
                          "role": "Written By",
                          "tracks": "",
                          "id": 0,
                          "resource_url": ""
                        },
                        {
                          "name": "Per Gessle",
                          "anv": "P. Gessle",
                          "join": "",
                          "role": "Written-By",
                          "tracks": "",
                          "id": 264046,
                          "resource_url": "https://api.discogs.com/artists/264046"
                        }
                      ],
                      "duration": "3:03"
                    },
                    {
                      "position": "A2",
                      "type_": "track",
                      "title": "Åh Amadeus",
                      "extraartists": [
                        {
                          "name": "P. Thyrén - F. Hansson",
                          "anv": "",
                          "join": "",
                          "role": "Written By",
                          "tracks": "",
                          "id": 0,
                          "resource_url": ""
                        },
                        {
                          "name": "Freddie Hansson",
                          "anv": "F. Hansson",
                          "join": "",
                          "role": "Written-By",
                          "tracks": "",
                          "id": 685761,
                          "resource_url": "https://api.discogs.com/artists/685761"
                        }
                      ],
                      "duration": "3:35"
                    }
                  ],
                  "extraartists": [
                    {
                      "name": "Rutger Gunnarsson",
                      "anv": "",
                      "join": "",
                      "role": "Arranged By",
                      "tracks": "",
                      "id": 266950,
                      "resource_url": "https://api.discogs.com/artists/266950"
                    },
                    {
                      "name": "Conny Ebegård",
                      "anv": "",
                      "join": "",
                      "role": "Engineer",
                      "tracks": "",
                      "id": 874881,
                      "resource_url": "https://api.discogs.com/artists/874881"
                    }
                  ],
                  "images": [
                    {
                      "type": "primary",
                      "uri": "",
                      "resource_url": "",
                      "uri150": "",
                      "width": 450,
                      "height": 450
                    },
                    {
                      "type": "secondary",
                      "uri": "",
                      "resource_url": "",
                      "uri150": "",
                      "width": 450,
                      "height": 450
                    }
                  ],
                  "thumb": "",
                  "estimated_weight": 230,
                  "blocked_from_sale": false
                }
                """;
    }

    public static String releaseInfoWithoutYear() {
        return """
                {
                  "title": "Kärleken Är Evig.",
                  "genres": [
                    "Electronic",
                    "Pop"
                  ],
                  "styles": [
                    "Synth-pop",
                    "Schlager"
                  ],
                  "tracklist": [
                    {
                      "position": "A1",
                      "type_": "track",
                      "title": "Kärleken Är Evig",
                      "duration": "3:03"
                    },
                    {
                      "position": "A2",
                      "type_": "track",
                      "title": "Åh Amadeus",
                      "duration": "3:35"
                    }
                  ]
                }
                """;
    }

    public static String lookupResponse() {
        return """
                {
                  "pagination": {
                   "page": 1,
                   "pages": 1,
                   "per_page": 50,
                   "items": 2,
                   "urls": {}
                  },
                  "results": [
                 
                   {
                    "country": "Sweden",
                    "year": "1986",
                    "format": ["Vinyl", "LP", "Album"],
                    "label": ["Mariann", "Mariann Records", "Mariann Records", "Fri Reklam", "KMH Studios", "SIB-Tryck AB, Norsborg", "Grammoplast"],
                    "type": "release",
                    "genre": ["Electronic", "Pop"],
                    "style": ["Synth-pop", "Schlager"],
                    "id": 2229646,
                    "barcode": ["BIEM/n\\u00a9b", "MLPH-1622-A 860414GP BA-GP", "MLPH-1622-B 860414GP BA-GP"],
                    "user_data": {
                     "in_wantlist": false,
                     "in_collection": false
                    },
                    "master_id": 315620,
                    "master_url": "https://api.discogs.com/masters/315620",
                    "uri": "/Lena-Philipsson-K%C3%A4rleken-%C3%84r-Evig/release/2229646",
                    "catno": "MLPH 1622",
                    "title": "Lena Philipsson - K\\u00e4rleken \\u00c4r Evig.",
                    "thumb": "https://img.discogs.com/K-iaOq7zlRC0q5RfRdA9U8VisCE=/fit-in/150x150/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/R-2229646-1320883037.jpeg.jpg",
                    "cover_image": "https://img.discogs.com/58RI-XKBhD42w1SKIJhXvUIMFqA=/fit-in/450x450/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-2229646-1320883037.jpeg.jpg",
                    "resource_url": "https://api.discogs.com/releases/2229646",
                    "community": {
                     "want": 20,
                     "have": 198
                    },
                    "format_quantity": 1,
                    "formats": [{
                     "name": "Vinyl",
                     "qty": "1",
                     "descriptions": ["LP", "Album"]
                    }]
                   },
                 
                   {
                    "country": "Sweden",
                    "year": "1986",
                    "format": ["Vinyl", "LP", "Album"],
                    "label": ["Mariann", "Mariann Records", "Mariann Records", "Fri Reklam", "KMH Studios", "SIB-Tryck AB, Norsborg", "Grammoplast"],
                    "type": "master",
                    "genre": ["Electronic", "Pop"],
                    "style": ["Synth-pop", "Schlager"],
                    "id": 315620,
                    "barcode": ["BIEM/n\\u00a9b", "MLPH-1622-A 860414GP BA-GP", "MLPH-1622-B 860414GP BA-GP"],
                    "user_data": {
                     "in_wantlist": false,
                     "in_collection": false
                    },
                    "master_id": 315620,
                    "master_url": "https://api.discogs.com/masters/315620",
                    "uri": "/Lena-Philipsson-K%C3%A4rleken-%C3%84r-Evig/master/315620",
                    "catno": "MLPH 1622",
                    "title": "Lena Philipsson - K\\u00e4rleken \\u00c4r Evig.",
                    "thumb": "https://img.discogs.com/K-iaOq7zlRC0q5RfRdA9U8VisCE=/fit-in/150x150/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/R-2229646-1320883037.jpeg.jpg",
                    "cover_image": "https://img.discogs.com/58RI-XKBhD42w1SKIJhXvUIMFqA=/fit-in/450x450/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-2229646-1320883037.jpeg.jpg",
                    "resource_url": "https://api.discogs.com/masters/315620",
                    "community": {
                     "want": 24,
                     "have": 207
                    }
                   }
                  ]
                 }
                """;
    }

    public static String productCreatedResponse() {
        return """
                {
                  "id": 14475806,
                  "private_reference": "REF-3"
                }
                """;
    }
}