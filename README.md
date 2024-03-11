# ElectionResults
**#Design and implement an election system as follows.**

**#There would be 25 contestants named A, B, C, ... , X, Y. There would be 5 regions participating in the election. The contestants vorting from each region is specified in an the input file voting.dat as follows:**

<<Region_name - string without any delimiters>>/<<contiguous_Contestants_list>>

Example:

Khammam/ABFMNTRY
Visakhapatnam/BCGLKP

The same contestant can contest from any number of regions (You may assume there would be not contestant name repetetion for one region)

The details of votes from each region are provided in the same input file as follows

//
<<region_name>>
<<voter_id>> <<contiguous_list_of_preferences>>
<<voter_id>> <<contiguous_list_of_preferences>>
<<voter_id>> <<contiguous_list_of_preferences>>
//
<<region_name>>
<<voter_id>> <<contiguous_list_of_preferences>>
<<voter_id>> <<contiguous_list_of_preferences>>
<<voter_id>> <<contiguous_list_of_preferences>>
<<voter_id>> <<contiguous_list_of_preferences>>
<<voter_id>> <<contiguous_list_of_preferences>>
<<voter_id>> <<contiguous_list_of_preferences>>
&&	

The '&&' implies End of input file.

A voter could vote for any three candidates contensting in his region in his order of preference. A vote is considered invalid in the following cases:

1. The voter of this vote has voted from someother region (identified by the voter id). 
2. The vote has zero or more than three preferences (one, two or three preferences in one vote is valid)
3. Any one of the preference in the vote is for a candidate not contesting in the region the voter has voted from

The election system must count the votes and release the winners as follows:

1. Every first preference carries 3 points, 2nd preference carries 2 points and last preference carries 1 point.
2. Candidate with the highest points over all the contesting regions will be the CHIEF OFFICER.
3. Of the remaining candidates, the candidate with the highest points of a region will be the REGIONAL HEAD.
4. Display the elected CHIEF OFFICER with the points.
5. Display each region with the number of invalidated votes and the region's elected REGIONAL HEAD with his points.
