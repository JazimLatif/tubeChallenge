package com.example.tubechallenge.presentation.ui.views

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import com.example.tubechallenge.presentation.viewmodel.StopViewModel
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tubechallenge.R
import com.example.tubechallenge.domain.model.Stop
import com.example.tubechallenge.domain.utils.Utils
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditStopScreen(viewModel: StopViewModel) {

    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf(viewModel.stopStationNameState) }
    val stations = listOf(
        "Acton Town", "Aldgate", "Aldgate East", "Alperton", "Amersham", "Angel", "Archway", "Arnos Grove", "Arsenal", "Baker Street",
        "Balham", "Bank", "Barbican", "Barking", "Barkingside", "Barons Court", "Battersea Power Station", "Bayswater", "Becontree", "Belsize Park",
        "Bermondsey", "Bethnal Green", "Blackfriars", "Blackhorse Road", "Bond Street", "Borough", "Boston Manor", "Bounds Green", "Bow Road", "Brent Cross",
        "Brixton", "Bromley-by-Bow", "Buckhurst Hill", "Burnt Oak", "Caledonian Road", "Camden Town", "Canada Water", "Canary Wharf", "Canning Town", "Cannon Street",
        "Canons Park", "Chalfont & Latimer", "Chalk Farm", "Chancery Lane", "Charing Cross", "Chesham", "Chigwell", "Chiswick Park", "Chorleywood", "Clapham Common",
        "Clapham North", "Clapham South", "Cockfosters", "Colindale", "Colliers Wood", "Covent Garden", "Croxley", "Dagenham East", "Dagenham Heathway", "Debden",
        "Dollis Hill", "Ealing Broadway", "Ealing Common", "Earl's Court", "East Acton", "East Finchley", "East Ham", "East Putney", "Eastcote", "Edgware",
        "Edgware Road", "Edgware Road", "Elephant & Castle", "Elm Park", "Embankment", "Epping", "Euston", "Euston Square", "Fairlop", "Farringdon",
        "Finchley Central", "Finchley Road", "Finsbury Park", "Fulham Broadway", "Gants Hill", "Gloucester Road", "Golders Green", "Goldhawk Road", "Goodge Street", "Grange Hill",
        "Great Portland Street", "Green Park", "Greenford", "Gunnersbury", "Hainault", "Hammersmith", "Hammersmith", "Hampstead", "Hanger Lane", "Harlesden",
        "Harrow & Wealdstone", "Harrow-on-the-Hill", "Hatton Cross", "Heathrow Terminal 4", "Heathrow Terminal 5", "Heathrow Terminals 2 & 3", "Hendon Central", "High Barnet", "High Street Kensington", "Highbury & Islington",
        "Highgate", "Hillingdon", "Holborn", "Holland Park", "Holloway Road", "Hornchurch", "Hounslow Central", "Hounslow East", "Hounslow West", "Hyde Park Corner",
        "Ickenham", "Kennington", "Kensal Green", "Kensington (Olympia)", "Kentish Town", "Kenton", "Kew Gardens", "Kilburn", "Kilburn Park", "King's Cross St Pancras",
        "Kingsbury", "Knightsbridge", "Ladbroke Grove", "Lambeth North", "Lancaster Gate", "Latimer Road", "Leicester Square", "Leyton", "Leytonstone", "Liverpool Street",
        "London Bridge", "Loughton", "Maida Vale", "Manor House", "Mansion House", "Marble Arch", "Marylebone", "Mile End", "Mill Hill East", "Monument",
        "Moor Park", "Moorgate", "Morden", "Mornington Crescent", "Neasden", "Newbury Park", "Nine Elms", "North Acton", "North Ealing", "North Greenwich",
        "North Harrow", "North Wembley", "Northfields", "Northolt", "Northwick Park", "Northwood", "Northwood Hills", "Notting Hill Gate", "Oakwood", "Old Street",
        "Osterley", "Oval", "Oxford Circus", "Paddington", "Paddington", "Park Royal", "Parsons Green", "Perivale", "Piccadilly Circus", "Pimlico",
        "Pinner", "Plaistow", "Preston Road", "Putney Bridge", "Queen's Park", "Queensbury", "Queensway", "Ravenscourt Park", "Rayners Lane", "Redbridge",
        "Regent's Park", "Richmond", "Rickmansworth", "Roding Valley", "Royal Oak", "Ruislip", "Ruislip Gardens", "Ruislip Manor", "Russell Square", "Seven Sisters",
        "Shepherd's Bush", "Shepherd's Bush Market", "Sloane Square", "Snaresbrook", "South Ealing", "South Harrow", "South Kensington", "South Kenton", "South Ruislip", "South Wimbledon",
        "South Woodford", "Southfields", "Southgate", "Southwark", "St James's Park", "St John's Wood", "St Paul's", "Stamford Brook", "Stanmore", "Stepney Green",
        "Stockwell", "Stonebridge Park", "Stratford", "Sudbury Hill", "Sudbury Town", "Swiss Cottage", "Temple", "Theydon Bois", "Tooting Bec", "Tooting Broadway",
        "Tottenham Court Road", "Tottenham Hale", "Totteridge & Whetstone", "Tower Hill", "Tufnell Park", "Turnham Green", "Turnpike Lane", "Upminster", "Upminster Bridge", "Upney",
        "Upton Park", "Uxbridge", "Vauxhall", "Victoria", "Walthamstow Central", "Wanstead", "Warren Street", "Warwick Avenue", "Waterloo", "Watford",
        "Wembley Central", "Wembley Park", "West Acton", "West Brompton", "West Finchley", "West Ham", "West Hampstead", "West Harrow", "West Kensington", "West Ruislip",
        "Westbourne Park", "Westminster", "White City", "Whitechapel", "Willesden Green", "Willesden Junction", "Wimbledon", "Wimbledon Park", "Wood Green", "Wood Lane",
        "Woodford", "Woodside Park"
    )

    val filteredStations = stations.filter {
        it.startsWith(searchText, ignoreCase = true)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.onStationNameChanged(it)
                expanded = true
            },
            label = { Text("Station Name") },
            modifier = Modifier.menuAnchor(),
            trailingIcon = {
                Utils.TrailingIcon(
                    R.drawable.baseline_delete_24,
                    onClick = { searchText = "" }
                )
            }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredStations.forEach { station ->
                DropdownMenuItem(
                    text = { Text(station) },
                    onClick = {
                        searchText = station
                        viewModel.onStationNameChanged(station)
                        expanded = false
                    }
                )
            }
        }
    }

    OutlinedTextField(
        value= viewModel.stopTimeArrivedState,
        label = { Text("Time Arrived") },
        onValueChange = {viewModel.onTimeArrivedChanged(it)},
        trailingIcon = {
            Utils.TrailingIcon(
                R.drawable.baseline_access_time_24,
                onClick = { viewModel.stopTimeArrivedState = Calendar.getInstance().time.toString() }
            )
        }
    )

    OutlinedTextField(
        value= viewModel.stopTimeDepartedState,
        label = { Text("Time Departed")},
        onValueChange = {viewModel.onTimeDepartedChanged(it)},
        trailingIcon = {
            Utils.TrailingIcon(
                R.drawable.baseline_access_time_24,
                onClick = { viewModel.stopTimeDepartedState = Calendar.getInstance().time.toString() }
            )
        }
    )

    OutlinedTextField(
        value= viewModel.stopNotesState,
        label = { Text("Notes") },
        onValueChange = { viewModel.onNotesChanged(it) },
        trailingIcon = { Utils.TrailingIcon(R.drawable.baseline_back_hand_24) {
            viewModel.stopNotesState = "Ignore last" }
        }
    )


    Button(
        onClick = {
            val stop = Stop(
                stationName = viewModel.stopStationNameState,
                timeArrived = viewModel.stopTimeArrivedState,
                timeDeparted = viewModel.stopTimeDepartedState,
                notes = viewModel.stopNotesState
            )
            if (stop.stationName.isNotEmpty() && stop.timeArrived.isNotEmpty() && stop.timeDeparted.isNotEmpty()) {

                viewModel.addStop(stop)

                Toast.makeText(context, "Logged ${viewModel.stopStationNameState}!", Toast.LENGTH_SHORT).show()

                searchText = ""
                viewModel.stopStationNameState = ""
                viewModel.stopTimeArrivedState = ""
                viewModel.stopTimeDepartedState = ""
                viewModel.stopNotesState = ""

            } else {
                Toast.makeText(context, "Please enter all main fields (notes optional)", Toast.LENGTH_SHORT).show()
            }
        },

    ) {
        Text("Log", modifier = Modifier.padding(4.dp))
        Icon(painterResource(R.drawable.baseline_edit_note_24), "log icon")

    }
}