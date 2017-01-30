using Caliburn.Micro;
using System.Collections.Generic;
using Tickets.Models;

namespace Tickets.ViewModels
{
    public class SetupTicketWithoutAccountPageViewModel : PropertyChangedBase
    {
        private readonly INavigationService _navigationService;
        private string _county = "București";

        public SetupTicketWithoutAccountPageViewModel(INavigationService navigationService)
        {
            _navigationService = navigationService;
        }

        public void NextStep()
        {
            _navigationService.For<SendTicketWithoutAccountPageViewModel>().Navigate();
        }

        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string Email { get; set; }
        public string PersonalNumericalCode { get; set; }
        public string Address { get; set; }

        public string Telephone { get; set; }

        public string County
        {
            get { return _county; }
            set { _county = value; NotifyOfPropertyChange(); }
        }

        public List<string> Counties => Ticket.Counties;
    }
}
