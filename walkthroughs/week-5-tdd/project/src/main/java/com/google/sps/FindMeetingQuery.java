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
    Collection<TimeRange> meetingTimes = new ArrayList<>();

    if(request.getDuration() > TimeRange.WHOLE_DAY.duration()){
        return meetingTimes;
    }
    
    ArrayList<TimeRange> conflictingEventTimes = new ArrayList<TimeRange>();
    for(Event event: events){
        if(checkAttending(event, request)){
            conflictingEventTimes.add(event.getWhen());
        }
    }
    
    if(conflictingEventTimes.size() == 0){
        meetingTimes.add(TimeRange.WHOLE_DAY);
        return meetingTimes;
    }

    Collections.sort(conflictingEventTimes, TimeRange.ORDER_BY_START);

    //check first event start time against beginning of day
    int firstEventStart = conflictingEventTimes.get(0).start();
    if((firstEventStart - TimeRange.START_OF_DAY) >= request.getDuration()){
        meetingTimes.add(TimeRange.fromStartEnd(TimeRange.START_OF_DAY, firstEventStart, false));
    }

    int latestEndTime = conflictingEventTimes.get(0).end();
    //checks all middle events to see if there is enough time between them
    for(int i = 0; i < conflictingEventTimes.size()-1; ++i){
        TimeRange firstEvent = conflictingEventTimes.get(i);
        TimeRange secondEvent = conflictingEventTimes.get(i+1);
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
