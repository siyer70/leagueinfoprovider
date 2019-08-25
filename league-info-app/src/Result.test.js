import React from 'react';
import { shallow } from 'enzyme';
import Result from './Result';
import requestResponseData from './reqresponsedata';

describe('Render Result Test', () => {
  it('Renders result table when result data is passed', () => {
    const positionData = requestResponseData.result;
    const component = shallow(<Result positionData={positionData} />);

    const table = component.find('table');
    expect(table).toHaveLength(1);

    const rows = table.find('tbody').find('tr');
    expect(rows).toHaveLength(4);

    const cellData = [
      positionData.countryInfo,
      positionData.leagueInfo,
      positionData.teamInfo,
      `${positionData.position}`
    ];

    rows.forEach((tr, rowIndex) => {
      const cells = tr.find('td');
      expect(cells.at(1).text()).toEqual(cellData[rowIndex]);
    });
  });
});
