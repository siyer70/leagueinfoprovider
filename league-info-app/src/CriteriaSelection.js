import React from 'react';
import './App.css';
class CriteriaSelection extends React.Component {
  constructor(props) {
    super(props);
    this.state = {selectedCriteria: 'audi', criteriaList: []};
    this.handleChange = this.handleChange.bind(this);
  }

  componentDidMount() {
    let apiURLPrefix = process.env.REACT_APP_API_URL_PREFIX;
    console.log(process.env.NODE_ENV);
    console.log(apiURLPrefix);
    let apiURL = apiURLPrefix + "/api/criterialist";
    console.log(apiURL);
    fetch(apiURL)
      .then((response) => {
        return response.json();
      })
      .then(data => {
        let criteraListFromAPI = data.map(clist => {
          let vstr = clist.countryName.concat('|', clist.leagueName, '|', clist.teamName);
          let dstr = 'Country='.concat(clist.countryName, ' League=', clist.leagueName, ' Team=', clist.teamName)
          return {value: vstr, display: dstr} 
        })
        this.setState({ criteriaList: [{value: '', display: '(Select criteria from the list)'}].concat(criteraListFromAPI) });
      }).catch(error => {
        console.log(error);
      });
  }


  handleChange(event) {
    this.setState({selectedCriteria: event.target.value});
    this.props.postChangeTrigger(event.target.value);
  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <div className="App">
          <label>Select Country, League and Team:</label>
          <span>&nbsp;</span>
          <select name="clist" value={this.state.selectedCriteria} onChange={this.handleChange}>
            {this.state.criteriaList.map((criteria) => <option key={criteria.value} value={criteria.value}>{criteria.display}</option>)}
          </select>
        </div>
      </form>
    );
  }
}

export default CriteriaSelection;
