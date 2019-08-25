import React from 'react';
import { mount, shallow } from 'enzyme';
import 'fetch-reply-with';
import App from './App';
import CriteriaSelection from './CriteriaSelection';
import Result from './Result';
import requestResponseData from './reqresponsedata';

describe('Main App Test', () => {
  let component;

  beforeAll(() => {
    fetch('http://localhost:8080/api/criterialist', {
      method: 'GET',
      replyWith: {
        status: 200,
        body: JSON.stringify(requestResponseData.criteriaListResponse),
        headers: {
          'Content-Type': 'application/json'
        }
      }
    }).then(res => {
      return res.json();
    });

    fetch(
      'http://localhost:8080/api/position/query?country_name=France&league_name=Ligue%202&team_name=AC%20Ajaccio',
      {
        method: 'GET',
        replyWith: {
          status: 200,
          body: JSON.stringify(requestResponseData.queryResponse),
          headers: {
            'Content-Type': 'application/json'
          }
        }
      }
    ).then(res => {
      return res.json();
    });
  });

  it('loads all child components using data from mocked backend calls', done => {
    component = mount(<App />);
    // process.nextTick(() => {
    //   const criteriaComponent = component.find(CriteriaSelection).first();
    //   const resultComponent = component.find(Result).first();
    //   criteriaComponent
    //     .find('#clist-id')
    //     .simulate('change', requestResponseData.event);
    //   component.forceUpdate();
    // });
    // process.nextTick(() => {
    //   expect(JSON.stringify(component.state('positionData'))).toEqual(
    //     JSON.stringify(requestResponseData.result)
    //   );
    //   // const rows = resultComponent.find('tbody').find('tr');
    //   // const cells = rows.at(4).find('td');
    //   // expect(cells.at(1).text()).toEqual('3');
    //   component.unmount();
    //   done();
    // });
    done();
  });

  it('loads position for the given criteria using mocked backend call', done => {
    component = shallow(<App />);
    component
      .instance()
      .updateResultPostChange(requestResponseData.event.target.value);
    process.nextTick(() => {
      expect(JSON.stringify(component.state('positionData'))).toEqual(
        JSON.stringify(requestResponseData.result)
      );
      done();
    });
  });

  afterAll(() => {
    component.unmount();
  });
});
