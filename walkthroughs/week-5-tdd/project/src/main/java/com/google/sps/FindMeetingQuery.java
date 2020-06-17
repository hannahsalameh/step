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
    System.out.print("\n Running code");
    ArrayList<Event> eventsArrayList = new ArrayList<Event>();
    Collection<TimeRange> meetingTimes = new ArrayList<>();

    if(request.getDuration() > TimeRange.WHOLE_DAY.duration()){
        return meetingTimes;
    }
    
    for(Event event: events){
        System.out.print(checkAttending(event,request));
        if(checkAttending(event, request)){
            eventsArrayList.add(event);
        }
    }

    if(request.getAttendees().size() == 0 || eventsArrayList.size() == 0){
        meetingTimes.add(TimeRange.WHOLE_DAY);
        return meetingTimes;
    }

    Event[] eventsArray = eventsArrayList.toArray(new Event[eventsArrayList.size()]);
    int latestEndTime = eventsArray[0].getWhen().end();
    for(int i = 0; i < eventsArray.length; ++i){
        if(i == 0){
            int firstEventStart = eventsArray[i].getWhen().start();
            if((firstEventStart - TimeRange.START_OF_DAY) >= request.getDuration()){
                meetingTimes.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, firstEventStart, false));
            }
        }
        if(i == eventsArray.length - 1){
            if((TimeRange.END_OF_DAY - latestEndTime) >= request.getDuration()){
                 meetingTimes.add(TimeRange.fromStartEnd(latestEndTime, TimeRange.END_OF_DAY, true));
            }
        } //else here bc it should work if i = 0, but not if off the edge
        else{
            TimeRange firstEvent = eventsArray[i].getWhen();
            TimeRange secondEvent = eventsArray[i+1].getWhen();
            if((secondEvent.start() - firstEvent.end()) >= request.getDuration() && !firstEvent.overlaps(secondEvent)){
                meetingTimes.add(TimeRange.fromStartEnd(firstEvent.end(),secondEvent.start(), false));
            }
            if(secondEvent.end() > latestEndTime){
                latestEndTime = secondEvent.end();
            }
        }
        System.out.print("latest time: " + latestEndTime);
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
