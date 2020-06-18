// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.*; 


public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    ArrayList<Event> eventsArrayList = new ArrayList<Event>();
    Collection<TimeRange> meetingTimes = new ArrayList<>();

    if(request.getDuration() > TimeRange.WHOLE_DAY.duration()){
        return meetingTimes;
    }
    
    for(Event event: events){
        if(checkAttending(event, request)){
            eventsArrayList.add(event);
        }
    }

    if(eventsArrayList.size() == 0){
        meetingTimes.add(TimeRange.WHOLE_DAY);
        return meetingTimes;
    }

    //sort by begin time
    //eventsArrayList could be more specific 
    //initialize > populate, not initialize > initilaize > populate

    //check first event start time against beginning of day
    int firstEventStart = eventsArrayList.get(0).getWhen().start();
    if((firstEventStart - TimeRange.START_OF_DAY) >= request.getDuration()){
        meetingTimes.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, firstEventStart, false));
    }

    int latestEndTime = eventsArrayList.get(0).getWhen().end();
    //checks all middle events to see if there is enough time between them
    for(int i = 1; i < eventsArrayList.size()-1; ++i){
        TimeRange firstEvent = eventsArrayList.get(i).getWhen();
        TimeRange secondEvent = eventsArrayList.get(i+1).getWhen();
        if((secondEvent.start() - firstEvent.end()) >= request.getDuration() && !firstEvent.overlaps(secondEvent)){
            meetingTimes.add(TimeRange.fromStartEnd(firstEvent.end(),secondEvent.start(), false));
        }
        if(secondEvent.end() > latestEndTime){
            latestEndTime = secondEvent.end();
        }
    }

    //check end time of lastest event against end of day
    if((TimeRange.END_OF_DAY - latestEndTime) >= request.getDuration()){
        meetingTimes.add(TimeRange.fromStartEnd(latestEndTime, TimeRange.END_OF_DAY, true));
    }
    return meetingTimes;
  }

  private boolean checkAttending(Event event, MeetingRequest request){
    Set<String> intersection = new HashSet<String>(event.getAttendees());
    intersection.retainAll(request.getAttendees());
     if(intersection.size() > 0){
         return true;
     }
     return false;
  }
}
